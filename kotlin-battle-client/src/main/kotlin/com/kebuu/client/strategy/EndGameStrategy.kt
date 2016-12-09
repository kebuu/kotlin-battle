package com.kebuu.client.strategy

import com.kebuu.client.StaticGameInfo
import com.kebuu.client.bean.GameView
import com.kebuu.core.action.FightAction
import com.kebuu.core.action.HealAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.board.spawn.SpawnAttributes

class EndGameStrategy : AbstractStepStrategy() {

    override fun spawnUpdate(point: Int): SpawnAttributes {
        val halfPoint = point / 2
        StaticGameInfo.spawnAttributes.force += halfPoint
        StaticGameInfo.spawnAttributes.shootDistance += (point - halfPoint)
        return StaticGameInfo.spawnAttributes
    }

    override fun action(gameView: GameView): StepAction {
        if (gameView.myLife <= 9) {
            return HealAction()
        }

        val somebodyToShoot = gameView.spawns
                .filter {it.gamerId != gameView.myGamerId}
                .first { it.position.distanceFrom(gameView.myPosition) <  gameView.mySpawnAttributes.shootDistance }

        return FightAction(somebodyToShoot.gamerId)
    }
}