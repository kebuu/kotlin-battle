package com.kebuu.server.game

import com.kebuu.core.Dimension
import com.kebuu.core.Position
import com.kebuu.core.board.Board
import com.kebuu.core.board.Hole
import com.kebuu.core.board.Mountain
import com.kebuu.core.board.Treasure
import com.kebuu.server.gamer.bot.DummyBot
import com.kebuu.server.gamer.bot.ImmobileBot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.util.*

@Component
class GameFactory @Autowired constructor(val random: Random) {

    fun configureBoardFor(game: Game) {
        val dimension = Dimension(getXorY(game.gamers.size), getXorY(game.gamers.size))

        activeGame.addGamers(DummyBot(), ImmobileBot())
        activeGame.board.addItem(Mountain(Position(3,3)))
        activeGame.board.addItem(Hole(Position(3,4)))
        activeGame.board.addItem(Treasure(Position(4,4)))

        game.board.setDimension(dimension)
    }

    private fun getXorY(numberOfGamers: Int) : Int {
        val side = Math.max(5, numberOfGamers / 2 )
        return side - random.nextInt(4) - 2
    }
}