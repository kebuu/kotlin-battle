package com.kebuu.client.service.impl

import com.kebuu.client.StaticGameInfo
import com.kebuu.client.bean.GameView
import com.kebuu.client.service.ActionService
import com.kebuu.core.action.StepAction
import com.kebuu.core.dto.GameInfo
import org.springframework.stereotype.Service

@Service
class ActionServiceImpl : ActionService {

    override fun action(gameInfo: GameInfo): StepAction {
        if (gameInfo.isFirstStep()) {
            StaticGameInfo.setStartInfo(gameInfo)
        }

        val gameView = GameView(gameInfo)
        StaticGameInfo.spawnAttributes = gameView.mySpawnAttributes
        return StaticGameInfo.findBestStrategy(gameView).action(gameView)
    }
}

fun GameInfo.isFirstStep(): Boolean {
    return this.board.dimension != null
}