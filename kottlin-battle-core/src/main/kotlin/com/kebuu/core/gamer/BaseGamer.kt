package com.kebuu.core.gamer

import com.kebuu.core.action.StepAction
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.dto.GameInfo

abstract class BaseGamer(val pseudo: String): Gamer {

    private var zPoints: Double = 0.0
    private var life: Int = 0

    override fun pseudo() = pseudo

    abstract fun doGetNextAction(gameInfo: GameInfo): StepAction
    abstract fun doGetSpawnAttributes(point: Int): SpawnAttributes

    override fun getNextAction(gameInfo: GameInfo): GamerAction {
        return GamerAction(this, doGetNextAction(gameInfo))
    }

    override fun getSpawnAttributes(point: Int): GamerSpawnAttributes {
        return GamerSpawnAttributes(this, doGetSpawnAttributes(point))
    }

    override fun getZPoints() = zPoints
    override fun getLife() = life

    override fun addZPoints(zPoints: Double) {
        this.zPoints += zPoints
    }

    override fun removeZPoints(zPoints: Double) {
        this.zPoints = Math.max(this.zPoints - zPoints, 0.0)
    }

    override fun removeZPointsPercent(percent: Double) {
        zPoints *= (1.0 - percent / 100.0)
    }

    override fun removeLife(life: Int) {
        this.life = Math.max(this.life - life, 0)
    }

    override fun setLife(life: Int) {
        this.life = life
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseGamer) return false

        if (pseudo != other.pseudo) return false

        return true
    }

    override fun hashCode(): Int {
        return pseudo.hashCode()
    }


}