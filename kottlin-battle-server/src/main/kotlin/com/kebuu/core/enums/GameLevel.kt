package com.kebuu.core.enums

import com.kebuu.core.action.FightAction
import com.kebuu.core.action.LimitedUseAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.board.BoardItem
import com.kebuu.core.board.Hole
import com.kebuu.core.board.Mountain
import com.kebuu.core.board.Treasure
import com.kebuu.server.gamer.bot.Bot

enum class GameLevel(val enableSpawnUpdate: Boolean,
                     val forbiddenActions: List<Class<out StepAction>>,
                     val boardItemsProportion: Map<Class<out BoardItem>, Int>,
                     val generateBotOfType: List<Class<out Bot>>) {

    LEVEL_0(false,
            listOf(FightAction::class.java, LimitedUseAction::class.java),
            mapOf(Treasure::class.java to 50),
            listOf()),
    LEVEL_1(false,
            listOf(FightAction::class.java),
            mapOf(Treasure::class.java to 20, Hole::class.java to 10, Mountain::class.java to 10),
            listOf()),
    LEVEL_2(true,
            listOf(),
            mapOf(Treasure::class.java to 30, Hole::class.java to 15, Mountain::class.java to 15),
            listOf())
}