package com.kebuu.core.comparator

import com.kebuu.core.action.*
import java.util.*

object ActionComparator: Comparator<StepAction> {

    val MIN_PRIORITY = Int.MAX_VALUE

    val actionPriorities = mapOf(
        HealthAction::class.java to 100,
        LightSpeedMoveAction::class.java to 200,
        MoveAction::class.java to 300,
        DigAction::class.java to 400,
        FightAction::class.java to 500
    )


    override fun compare(gamerAction1: StepAction?, gamerAction2: StepAction?): Int {
        val action1Priority = actionPriorities[gamerAction1!!.javaClass] ?: MIN_PRIORITY
        val action2Priority = actionPriorities[gamerAction2!!.javaClass] ?: MIN_PRIORITY
        return action1Priority.compareTo(action2Priority)
    }
}