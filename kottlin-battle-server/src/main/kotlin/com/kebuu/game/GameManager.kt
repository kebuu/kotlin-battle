package com.kebuu.game

import com.kebuu.Position
import com.kebuu.board.Hole
import com.kebuu.board.Mountain
import com.kebuu.board.ZPoint
import com.kebuu.enums.GameStatus
import com.kebuu.gamer.Gamer
import com.kebuu.gamer.bot.DummyBot
import com.kebuu.gamer.bot.ImmobileBot
import com.kebuu.websocket.WebSockerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger


@Component
class GameManager @Autowired constructor(val webSockerService: WebSockerService) {
    var games: MutableList<Game> = mutableListOf()
    val idGenerator = AtomicInteger()

    fun createGame(): Game {
        val game = Game(idGenerator.incrementAndGet())
        games.add(game)
        return game
    }

    fun addGamer(gamer: Gamer) {
        getActiveGame().addGamers(gamer)
    }

    fun start() {
        val activeGame = getActiveGame()

        activeGame.addGamers(DummyBot(), ImmobileBot())
        activeGame.board.addItem(Mountain(Position(3,3)))
        activeGame.board.addItem(Hole(Position(3,4)))
        activeGame.board.addItem(ZPoint(position = Position(4,4)))


        activeGame.init()

        webSockerService.sendGame(activeGame)

        for(step in 1..3 ) {
            activeGame.runStep(step)
            webSockerService.sendGame(activeGame)
            Thread.sleep(1000)
        }

        end()
    }

    private fun end() {
        getActiveGame().end()
        //webSockerService.sendGame(getActiveGame())
    }

    fun getActiveGame() = games.firstOrNull { it.status != GameStatus.STOPPED } ?: createGame()
}

