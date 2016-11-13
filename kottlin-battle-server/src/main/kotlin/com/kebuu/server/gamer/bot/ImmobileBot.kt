package com.kebuu.server.gamer.bot

import com.kebuu.core.action.NoAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.dto.GameInfo
import com.kebuu.core.gamer.BaseGamer

class ImmobileBot private constructor(pseudo: String): BaseGamer(pseudo), Bot {

    constructor(): this("ImmobileBot-" + Bot.COUNTER.andIncrement)

    override fun doGetNextAction(gameInfo: GameInfo): StepAction {
        return NoAction()
    }

    override fun doGetSpawnAttributes(point: Int): SpawnAttributes {
        return SpawnAttributes()
    }
}

