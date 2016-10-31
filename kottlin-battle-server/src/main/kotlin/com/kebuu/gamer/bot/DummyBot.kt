package com.kebuu.gamer.bot

import com.kebuu.Position
import com.kebuu.action.StepAction
import com.kebuu.action.MoveAction
import com.kebuu.action.NoAction
import com.kebuu.board.spawn.SpawnAttributes
import com.kebuu.dto.GameInfo
import com.kebuu.gamer.BaseGamer

class DummyBot private constructor(pseudo: String): BaseGamer(pseudo){

    constructor(): this("DummyBot-" + Bot.COUNTER.andIncrement )

    override fun getNextAction(gameInfo: GameInfo): StepAction {
        val currentPosition = gameInfo.currentPosition()

        val operations = listOf(
            { position: Position -> position.plusX(1)},
            { position: Position -> position.plusY(1)},
            { position: Position -> position.plusX(-1)},
            { position: Position -> position.plusY(-1)}
        )

        return operations.map { it(currentPosition) }
                .map(::MoveAction)
                .firstOrNull { gameInfo.game.board.isOnBoard(it.goTo) }
                ?: NoAction()
    }

    override fun getSpawnAttributes(point: Int): SpawnAttributes {
        return SpawnAttributes()
    }


}

