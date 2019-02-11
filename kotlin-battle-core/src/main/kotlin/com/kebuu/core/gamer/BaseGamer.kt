package com.kebuu.core.gamer

import com.kebuu.core.action.StepAction
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.dto.GameInfo

abstract class BaseGamer(val userId: String, private var zPoints: Int = 100): Gamer {

    private var life: Int = 0

    override fun gamerId() = userId
    override fun shortName() = userId.substringBefore('@')

    abstract fun doGetNextAction(gameInfo: GameInfo): StepAction
    abstract fun doGetSpawnAttributes(point: Int): SpawnAttributes

    override fun getNextAction(gameInfo: GameInfo): GamerAction =
        GamerAction(this, doGetNextAction(gameInfo))

    override fun getSpawnAttributes(point: Int): GamerSpawnAttributes =
        GamerSpawnAttributes(this, doGetSpawnAttributes(point))

    override fun getZPoints() = zPoints

    override fun getLife() = life

    override fun gainZPoints(zPoints: Int) {
        this.zPoints += zPoints
    }

    override fun loseZPoints(zPoints: Int) {
        this.zPoints = Math.max(this.zPoints - zPoints, 0)
    }

    override fun removeZPointsPercent(percent: Int) {
        zPoints *= (1 - percent / 100)
    }

    override fun loseLife(life: Int) {
        this.life = Math.max(this.life - life, 0)
    }

    override fun setLife(life: Int) {
        this.life = life
    }

    override fun setZPoints(zPoints: Int) {
        this.zPoints = zPoints
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseGamer) return false

        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int = userId.hashCode()
}