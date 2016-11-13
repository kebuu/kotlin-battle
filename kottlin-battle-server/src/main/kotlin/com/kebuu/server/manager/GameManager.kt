package com.kebuu.server.manager

import com.kebuu.core.enums.GameLevel
import com.kebuu.core.enums.GameStatus
import com.kebuu.core.gamer.RemoteGamer
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
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


@Component
class GameManager @Autowired constructor(val webSocketService: WebSocketService,
                                         val userRegistryService: UserRegistryService,
                                         val eventLogService: EventLogService,
                                         val gameFactory: GameFactory,
                                         val restTemplate: RestTemplate,
                                         val gameConfig: GameConfig) {

    companion object {
        val NO_INITIAL_DELAY: Long = 0
    }

    var games: MutableList<Game> = mutableListOf()
    val idGenerator = AtomicInteger()
    val executor = Executors.newScheduledThreadPool(1)!!

    fun createGame(gameLevel: GameLevel = GameLevel.LEVEL_0): Game {
        val game = Game(idGenerator.incrementAndGet(), gameConfig, eventLogService, gameLevel)
        games.add(game)
        return game
    }

    fun start() {
        val activeGame = activeGame()

        gameFactory.configureBoardFor(activeGame)

        activeGame.init()

        webSocketService.sendGame(activeGame)

        runSteps(activeGame)

        end()
    }

    private fun runSteps(activeGame: Game) {
        val stepCounter = AtomicInteger()

        executor.scheduleAtFixedRate({
            activeGame.runStep(stepCounter.andIncrement)
            webSocketService.sendGame(activeGame)

            if (activeGame.isOver()) {
                executor.shutdown()
            }
        }, GameManager.NO_INITIAL_DELAY, gameConfig.gameStepDurationSecond, TimeUnit.SECONDS)
    }

    private fun end() {
        activeGame().end()
        //webSockerService.sendGame(activeGame())
    }

    fun activeGame() = activeGameOrNull() ?: throw IllegalStateException("Pas de jeu active disponible")

    fun activeGameOrNull() = games.firstOrNull { it.status != GameStatus.STOPPED }

    fun register(name: String, host: String, port: Int) {
        val user = userRegistryService.getUser(name) ?: throw UnknownUserException(name)

        val remoteGamer = RemoteGamer(name, host, port, user.avatarUrl, restTemplate)
        activeGame().register(remoteGamer)
    }

    fun unregister(name: String) = activeGame().unregister(name)
}


