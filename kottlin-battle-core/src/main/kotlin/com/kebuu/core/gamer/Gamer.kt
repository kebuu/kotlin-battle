package com.kebuu.core.gamer

import com.kebuu.core.dto.GameInfo

interface Gamer {

    fun pseudo(): String

    fun getNextAction(gameInfo: GameInfo): GamerAction

    fun getSpawnAttributes(point: Int): GamerSpawnAttributes

    fun getZPoints(): Double

    fun addZPoints(double: Double)

    fun removeZPoints(double: Double)

    fun removeZPointsPercent(percent: Double)
}

