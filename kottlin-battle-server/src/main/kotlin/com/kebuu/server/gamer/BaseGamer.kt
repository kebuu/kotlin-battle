package com.kebuu.server.gamer

import com.kebuu.core.action.StepAction
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.dto.GameInfo
import com.kebuu.core.gamer.Gamer
import com.kebuu.core.gamer.GamerAction
import com.kebuu.core.gamer.GamerSpawnAttributes

abstract class BaseGamer(val pseudo: String): Gamer {

    private var zPoints: Double = 0.0

    abstract fun doGetNextAction(gameInfo: GameInfo): StepAction
    abstract fun doGetSpawnAttributes(point: Int): SpawnAttributes

    override fun getNextAction(gameInfo: GameInfo): GamerAction {
        return GamerAction(this, doGetNextAction(gameInfo))
    }

    override fun getSpawnAttributes(point: Int): GamerSpawnAttributes {
        return GamerSpawnAttributes(this, doGetSpawnAttributes(point))
    }

    override fun getZPoints() = zPoints

    override fun addZPoints(double: Double) {
        zPoints += double
    }

    override fun removeZPoints(double: Double) {
        zPoints -= double
    }

    override fun removeZPointsPercent(percent: Double) {
        zPoints *= (1.0 - percent / 100.0)
    }

    override fun pseudo() = pseudo

}