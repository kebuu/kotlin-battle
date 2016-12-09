package com.kebuu.client.strategy

import com.kebuu.client.StaticGameInfo
import com.kebuu.client.bean.GameView
import com.kebuu.core.action.*
import com.kebuu.core.utils.mapTo

class TreasureHunterStrategy : AbstractStepStrategy() {

    override fun action(gameView: GameView): StepAction {
        val closestTreasure = gameView.treasures.minBy { gameView.myPosition.distanceFrom(it.position) }

        return if (closestTreasure?.position == gameView.myPosition) {
            DigAction()
        } else {
            val biggestTreasure = gameView.treasures.maxBy { it.zPoints }
            val lightSpeedMoveAction = biggestTreasure?.mapTo {
                StaticGameInfo.iUsedLightSpeedAction()
                LightSpeedMoveAction(MoveAction(it.position))
            }

            lightSpeedMoveAction ?: NoAction()
        }
    }
}