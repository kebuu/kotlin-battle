package com.kebuu.server.gamer.bot

import com.kebuu.core.Position
import com.kebuu.core.action.MoveAction
import com.kebuu.core.action.NoAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.dto.GameInfo
import com.kebuu.core.gamer.BaseGamer

class DummyBot private constructor(pseudo: String): BaseGamer(pseudo), Bot {

    constructor(): this("DummyBot-" + Bot.COUNTER.andIncrement )

    companion object {
        val operations = listOf(
            { position: Position -> position.plusX(1)},
            { position: Position -> position.plusY(1)},
            { position: Position -> position.plusX(-1)},
            { position: Position -> position.plusY(-1)}
        )
    }

    override fun doGetNextAction(gameInfo: GameInfo): StepAction {
        val currentPosition = gameInfo.currentPosition()

        return operations.map { it(currentPosition) }
                .map(::MoveAction)
                .firstOrNull { gameInfo.board.isOnBoard(it.goTo) }
                ?: NoAction()
    }

    override fun doGetSpawnAttributes(point: Int): SpawnAttributes {
        return SpawnAttributes()
    }
}

