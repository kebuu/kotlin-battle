package com.kebuu.gamer

import com.kebuu.action.StepAction
import com.kebuu.board.spawn.SpawnAttributes
import com.kebuu.dto.GameInfo

interface Gamer {

    fun pseudo(): String

    fun getNextAction(gameInfo: GameInfo): StepAction

    fun getSpawnAttributes(point: Int): SpawnAttributes

    fun getZPoints(): Double

    fun addZPoints(double: Double)

    fun removeZPoints(double: Double)

    fun removeZPointsPercent(percent: Double)
}

