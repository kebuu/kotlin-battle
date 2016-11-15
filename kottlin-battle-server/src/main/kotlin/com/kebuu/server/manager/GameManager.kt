package com.kebuu.server.manager

import com.kebuu.core.enums.GameLevel
import com.kebuu.core.enums.GameStatus
import com.kebuu.core.gamer.RemoteGamer
import com.kebuu.core.utils.Loggable
import com.kebuu.server.config.GameConfig
import com.kebuu.server.exception.UnknownUserException
import com.kebuu.server.game.Game
import com.kebuu.server.game.GameFactory
import com.kebuu.server.service.EventLogService
import com.kebuu.server.service.UserRegistryService
import com.kebuu.server.service.WebSocketService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


@Component
class GameManager @Autowired constructor(val webSocketService: WebSocketService,
                                         val userRegistryService: UserRegistryService,
                                         val eventLogService: EventLogService,
                                         val gameFactory: GameFactory,
                                         val restTemplate: RestTemplate,
                                         val gameConfig: GameConfig) : Loggable {

    companion object {
        val NO_INITIAL_DELAY: Long = 0
    }

    val mappedScheduledTask = mutableMapOf<Game, ScheduledFuture<*>>()
    val games: MutableList<Game> = mutableListOf()
    val idGenerator = AtomicInteger()
    val executor = Executors.newScheduledThreadPool(1)!!

    fun createGame(gameLevel: GameLevel = GameLevel.LEVEL_0): Game {
        val game = Game(idGenerator.incrementAndGet(), gameConfig, eventLogService, gameLevel)
        games.add(game)
        webSocketService.sendGame(activeGame())
        return game
    }

    fun start() {
        logger.info("Starting game")
        val activeGame = activeGame()

        logger.info("Configuring game board")
        gameFactory.configureBoardFor(activeGame)

        logger.info("Configuring game board")
        activeGame.init()

        webSocketService.sendGame(activeGame)

        logger.info("Running steps")
        runSteps(activeGame)
    }

    private fun runSteps(activeGame: Game) {
        val stepCounter = AtomicInteger()

        val scheduledTask = executor.scheduleAtFixedRate({
            activeGame.runStep(stepCounter.andIncrement)
            webSocketService.sendGame(activeGame)

            if (activeGame.isOver()) {
                logger.info("Game is over")
                mappedScheduledTask[activeGame]!!.cancel(false)
                mappedScheduledTask.remove(activeGame)
                end()
            }
        }, GameManager.NO_INITIAL_DELAY, gameConfig.gameStepDurationSecond, TimeUnit.SECONDS)

        mappedScheduledTask.put(activeGame, scheduledTask)
    }

    private fun end() {
        val activeGame = activeGame()
        activeGame.end()
        webSocketService.sendGame(activeGame)
    }

    fun activeGame() = activeGameOrNull() ?: throw IllegalStateException("Pas de jeu actif disponible")

    fun activeGameOrNull() = games.firstOrNull { it.status != GameStatus.STOPPED }

    fun register(email: String, host: String, port: Int) {
        val user = userRegistryService.getUser(email) ?: throw UnknownUserException(email)

        val remoteGamer = RemoteGamer(email, host, port, user.avatarUrl, restTemplate)
        val activeGame = activeGame()
        activeGame.register(remoteGamer)
        webSocketService.sendGame(activeGame)
    }

    fun unregister(email: String) {
        val activeGame = activeGame()
        activeGame.unregister(email)
        webSocketService.sendGame(activeGame)
    }

    fun stop() {
        activeGame().end()
    }
}


