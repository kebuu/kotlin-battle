package com.kebuu.core.action.execution

import com.kebuu.core.action.*
import com.kebuu.core.action.error.ExceptionAction
import com.kebuu.core.action.error.TimeoutAction

interface ActionExecutor {

    fun execute(noAction: NoAction): String

    fun execute(digAction: DigAction): String

    fun execute(fightAction: FightAction): String

    fun execute(healAction: HealAction): String

    fun execute(lightSpeedMoveAction: LightSpeedMoveAction): String

    fun execute(moveAction: MoveAction): String

    fun execute(exceptionAction: ExceptionAction): String

    fun execute(timeoutAction: TimeoutAction): String
}