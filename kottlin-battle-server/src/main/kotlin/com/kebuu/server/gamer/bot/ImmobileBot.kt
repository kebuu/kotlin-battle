package com.kebuu.server.gamer.bot

import com.kebuu.core.action.NoAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.dto.GameInfo
import com.kebuu.server.gamer.BaseGamer

class ImmobileBot private constructor(pseudo: String): BaseGamer(pseudo){

    constructor(): this("ImmobileBot-" + Bot.COUNTER.andIncrement)

    override fun doGetNextAction(gameInfo: GameInfo): StepAction {
        return NoAction()
    }

    override fun doGetSpawnAttributes(point: Int): SpawnAttributes {
        return SpawnAttributes()
    }
}

