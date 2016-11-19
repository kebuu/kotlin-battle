package com.kebuu.core.gamer

import com.kebuu.core.dto.GameInfo

interface Gamer {

    fun pseudo(): String

    fun getNextAction(gameInfo: GameInfo): GamerAction

    fun getSpawnAttributes(point: Int): GamerSpawnAttributes

    fun getZPoints(): Int

    fun gainZPoints(zPoints: Int)

    fun loseZPoints(zPoints: Int)

    fun removeZPointsPercent(percent: Int)

    fun getLife(): Int

    fun loseLife(life: Int)

    fun setLife(life: Int)

    fun isDead() = getLife() == 0
}

