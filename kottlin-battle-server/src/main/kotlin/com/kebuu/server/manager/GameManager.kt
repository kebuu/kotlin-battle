package com.kebuu.server.manager

import com.kebuu.core.enums.GameStatus
import com.kebuu.core.gamer.Gamer
import com.kebuu.core.gamer.RemoteGamer
import com.kebuu.server.config.GameConfig
import com.kebuu.server.exception.UnknownUserException
import com.kebuu.server.game.Game
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
                                         val restTemplate: RestTemplate,
                                         val gameConfig: GameConfig) {

    companion object {
        val NO_INITIAL_DELAY: Long = 0
    }

    var games: MutableList<Game> = mutableListOf()
    val idGenerator = AtomicInteger()
    val executor = Executors.newScheduledThreadPool(1)!!

    fun createGame(): Game {
        val game = Game(idGenerator.incrementAndGet(), gameConfig, eventLogService)
        games.add(game)
        return game
    }

    fun addGamer(gamer: Gamer) {
        getActiveGame().addGamers(gamer)
    }

    fun start() {
        val activeGame = getActiveGame()

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
        getActiveGame().end()
        //webSockerService.sendGame(getActiveGame())
    }

    fun getActiveGame() = games.firstOrNull { it.status != GameStatus.STOPPED } ?: createGame()

    fun register(name: String, host: String, port: Int) {
        val user = userRegistryService.getUser(name) ?: throw UnknownUserException(name)

        val remoteGamer = RemoteGamer(name, host, port, user.avatarUrl, restTemplate)
        getActiveGame().register(remoteGamer)
    }

    fun unregister(name: String) = getActiveGame().unregister(name)
}


