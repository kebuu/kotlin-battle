package com.kebuu.client.strategy

import com.kebuu.client.StaticGameInfo
import com.kebuu.client.bean.GameView
import com.kebuu.core.Dimension
import com.kebuu.core.Position
import com.kebuu.core.action.DigAction
import com.kebuu.core.action.MoveAction
import com.kebuu.core.action.NoAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.utils.mapTo

class StartGameStrategy : AbstractStepStrategy() {

    override fun action(gameView: GameView): StepAction {
        val closestTreasure = gameView.treasures.minBy { gameView.myPosition.distanceFrom(it.position) }

        return if (closestTreasure?.position == gameView.myPosition) {
            DigAction()
        } else {
            val nextAction = closestTreasure?.let {
                val closestPosition = StaticGameInfo.dimension.findValidClosestPosition(gameView.myPosition, closestTreasure.position, StaticGameInfo.spawnAttributes.speed)
                closestPosition?.mapTo { MoveAction(closestPosition) }
            }

            nextAction ?: NoAction()
        }
    }
}

fun Dimension.findValidClosestPosition(fromPosition: Position, toPosition: Position, speed: Int): Position? {
    return this.filter { it !in StaticGameInfo.invalidPositions() }
            .filter { it.distanceFrom(fromPosition) <= speed }
            .minBy { it.distanceFrom(toPosition) }
}
