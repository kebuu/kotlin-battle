package com.kebuu.client.strategy

import com.kebuu.client.bean.GameView
import com.kebuu.core.action.StepAction
import com.kebuu.core.board.spawn.SpawnAttributes


interface StepStrategy {

    fun spawnUpdate(point: Int): SpawnAttributes

    fun action(gameView: GameView): StepAction
}