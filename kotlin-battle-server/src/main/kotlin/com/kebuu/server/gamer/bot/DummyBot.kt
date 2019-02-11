package com.kebuu.server.gamer.bot

import com.kebuu.core.Dimension
import com.kebuu.core.Position
import com.kebuu.core.action.MoveAction
import com.kebuu.core.action.NoAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.bot.Bot
import com.kebuu.core.dto.GameInfo
import com.kebuu.core.gamer.BaseGamer

class DummyBot constructor(
        gamerId: String = "DummyBot-${Bot.COUNTER.andIncrement}",
        override val type: String = "dummyBot"
) : BaseGamer(gamerId), Bot {

    lateinit var boardDimension: Dimension

    companion object {
        val operations = listOf(
                { position: Position -> position.plusX(1) },
                { position: Position -> position.plusY(1) },
                { position: Position -> position.plusX(-1) },
                { position: Position -> position.plusY(-1) }
        )
    }

    override fun doGetNextAction(gameInfo: GameInfo): StepAction {
        if (gameInfo.board.dimension != null) {
            boardDimension = gameInfo.board.dimension!!
        }

        return operations.map { it(gameInfo.position) }
                       .map(::MoveAction)
                       .firstOrNull {
                           it.goTo.x in 0..(boardDimension.x - 1) && it.goTo.y in 0..(boardDimension.y - 1)
                       }
               ?: NoAction()
    }

    override fun doGetSpawnAttributes(point: Int): SpawnAttributes = SpawnAttributes(speed = 1)
}

