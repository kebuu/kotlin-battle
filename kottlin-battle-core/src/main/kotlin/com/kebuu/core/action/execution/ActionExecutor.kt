package com.kebuu.core.action.execution

import com.kebuu.core.action.*

interface ActionExecutor {

    fun execute(noAction: NoAction): String

    fun execute(noAction: DigAction): String

    fun execute(fightAction: FightAction): String

    fun execute(healthAction: HealthAction): String

    fun execute(lightSpeedMoveAction: LightSpeedMoveAction): String

    fun execute(moveAction: MoveAction): String
}