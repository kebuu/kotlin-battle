package com.kebuu.client

import com.kebuu.client.bean.GameView
import com.kebuu.client.strategy.EndGameStrategy
import com.kebuu.client.strategy.StartGameStrategy
import com.kebuu.client.strategy.StepStrategy
import com.kebuu.client.strategy.TreasureHunterStrategy
import com.kebuu.core.Dimension
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.dto.GameInfo
import com.kebuu.core.dto.board.item.AbstractBoardItemDto
import com.kebuu.core.dto.board.item.HoleBoardItemDto
import com.kebuu.core.dto.board.item.MountainBoardItemDto

object StaticGameInfo {

    lateinit var dimension: Dimension
    lateinit var lastStepNumber: Integer
    var staticBoardItems = mutableListOf<AbstractBoardItemDto>()
    var spawnAttributes = SpawnAttributes()
    var usableLightSpeedAction = 4
    var gameStep = 1

    fun setStartInfo(gameInfo: GameInfo) {
        dimension = gameInfo.board.dimension!!
        lastStepNumber = Integer(gameInfo.lastStepNumber!!)
        staticBoardItems = gameInfo.board.boardItems.filter { it is HoleBoardItemDto || it is MountainBoardItemDto }.toMutableList()
        spawnAttributes = gameInfo.spawnAttributes
        usableLightSpeedAction = 4
        spawnAttributes = SpawnAttributes()
        gameStep = 0
    }

    fun canIUseLightSpeedAction() = usableLightSpeedAction > 0

    fun iUsedLightSpeedAction() = --usableLightSpeedAction

    fun invalidPositions() = staticBoardItems.map { it.position }

    fun findBestStrategy(gameView: GameView): StepStrategy {
        gameStep++

        return if (StaticGameInfo.canIUseLightSpeedAction()) {
            TreasureHunterStrategy()
        } else if(gameStep >= lastStepNumber.toInt() / 2 && gameView.canIShootSomeone()) {
            EndGameStrategy()
        } else {
            StartGameStrategy()
        }
    }

    fun findBestStrategy(): StepStrategy {
        return if (StaticGameInfo.canIUseLightSpeedAction()) {
            TreasureHunterStrategy()
        } else if(gameStep >= lastStepNumber.toInt() / 2) {
            EndGameStrategy()
        } else {
            StartGameStrategy()
        }
    }
}

private fun GameView.canIShootSomeone(): Boolean {
    return this.spawns.filter {it.gamerId != myGamerId}.any { it.position.distanceFrom(myPosition) <  mySpawnAttributes.shootDistance }
}
