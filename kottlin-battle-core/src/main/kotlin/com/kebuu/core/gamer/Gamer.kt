package com.kebuu.core.gamer

import com.kebuu.core.dto.GameInfo

interface Gamer {

    fun pseudo(): String

    fun getNextAction(gameInfo: GameInfo): GamerAction

    fun getSpawnAttributes(point: Int): GamerSpawnAttributes

    fun getZPoints(): Double

    fun addZPoints(zPoints: Double)

    fun removeZPoints(zPoints: Double)

    fun removeZPointsPercent(percent: Double)

    fun getLife(): Int

    fun removeLife(life: Int)

    fun setLife(life: Int)

    fun isDead() = getLife() == 0
}

