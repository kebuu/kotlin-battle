package com.kebuu.gamer.bot

import com.kebuu.action.StepAction
import com.kebuu.action.NoAction
import com.kebuu.board.spawn.SpawnAttributes
import com.kebuu.dto.GameInfo
import com.kebuu.gamer.BaseGamer

class ImmobileBot private constructor(pseudo: String): BaseGamer(pseudo){

    constructor(): this("ImmobileBot-" + Bot.COUNTER.andIncrement )

    override fun getNextAction(gameInfo: GameInfo): StepAction {
        return NoAction()
    }

    override fun getSpawnAttributes(point: Int): SpawnAttributes {
        return SpawnAttributes()
    }
}

