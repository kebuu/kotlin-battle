package com.kebuu.server.game

import com.kebuu.core.Position
import com.kebuu.core.board.Hole
import com.kebuu.core.board.Mountain
import com.kebuu.core.board.ZPoint
import com.kebuu.core.enums.GameStatus
import com.kebuu.core.game.Game
import com.kebuu.core.gamer.Gamer
import com.kebuu.core.config.GameConfig
import com.kebuu.server.gamer.bot.DummyBot
import com.kebuu.server.gamer.bot.ImmobileBot
import com.kebuu.server.websocket.WebSockerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


@Component
class GameManager @Autowired constructor(val webSockerService: WebSockerService,
                                         val gameConfig: GameConfig) {
    var games: MutableList<Game> = mutableListOf()
    val idGenerator = AtomicInteger()
    val executor = Executors.newScheduledThreadPool(1)!!

    fun createGame(): Game {
        val game = Game(idGenerator.incrementAndGet(), gameConfig)
        games.add(game)
        return game
    }

    fun addGamer(gamer: Gamer) {
        getActiveGame().addGamers(gamer)
    }

    fun start() {
        val activeGame = getActiveGame()

        // TODO remove
        activeGame.addGamers(DummyBot(), ImmobileBot())
        activeGame.board.addItem(Mountain(Position(3,3)))
        activeGame.board.addItem(Hole(Position(3,4)))
        activeGame.board.addItem(ZPoint(position = Position(4,4)))


        activeGame.init()

        webSockerService.sendGame(activeGame)

        val stepCounter = AtomicInteger()
        executor.scheduleAtFixedRate({
            activeGame.runStep(stepCounter.andIncrement)
            webSockerService.sendGame(activeGame)

            if (activeGame.isOver()) {
                executor.shutdown()
            }
        }, 0, 1, TimeUnit.SECONDS)

        end()
    }

    private fun end() {
        getActiveGame().end()
        //webSockerService.sendGame(getActiveGame())
    }

    fun getActiveGame() = games.firstOrNull { it.status != GameStatus.STOPPED } ?: createGame()
}

