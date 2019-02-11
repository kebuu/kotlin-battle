package com.kebuu.server.gamer.bot

import com.kebuu.core.action.NoAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.bot.Bot
import com.kebuu.core.dto.GameInfo
import com.kebuu.core.gamer.BaseGamer

class ImmobileBot constructor(
        gamerId: String = "ImmobileBot-${Bot.COUNTER.andIncrement}",
        override val type: String = "immobileBot"
) : BaseGamer(gamerId), Bot {

    override fun doGetNextAction(gameInfo: GameInfo): StepAction = NoAction()

    override fun doGetSpawnAttributes(point: Int): SpawnAttributes = SpawnAttributes()
}

