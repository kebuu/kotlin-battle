package com.kebuu.server.game

import com.kebuu.core.Dimension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class GameFactory @Autowired constructor(val random: Random) {

    fun configureBoardFor(game: Game) {
        val dimension = Dimension(calculateXYDimension(game.gamers.size), calculateXYDimension(game.gamers.size))
        game.board.setDimension(dimension)

        for((boardItemGenerator, proportion) in game.level.boardItemGenerators) {
            val numberOfItem = dimension.numberOfCells * proportion / 100

            for (i in 1..numberOfItem) {
                game.board.addItem(boardItemGenerator(game))
            }
        }

        for((botGenerator, proportion) in game.level.botGenerators) {
            val numberOfItem = dimension.numberOfCells * proportion / 100

            for (i in 1..numberOfItem) {
                game.addGamers(botGenerator(game))
            }
        }
    }

    private fun calculateXYDimension(numberOfGamers: Int) : Int {
        val side = Math.max(10, Math.ceil(numberOfGamers.toDouble() / 1.2 ).toInt())
        return side - (random.nextInt(7) - 3)
    }
}