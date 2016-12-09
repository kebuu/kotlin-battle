package com.kebuu.client.strategy

import com.kebuu.client.StaticGameInfo
import com.kebuu.core.board.spawn.SpawnAttributes

abstract class AbstractStepStrategy : StepStrategy {

    override fun spawnUpdate(point: Int): SpawnAttributes {
        StaticGameInfo.spawnAttributes.speed += point
        return StaticGameInfo.spawnAttributes
    }
}