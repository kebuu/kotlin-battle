package com.kebuu.client.bean

import com.kebuu.core.dto.GameInfo
import com.kebuu.core.dto.board.item.AbstractBoardItemDto
import com.kebuu.core.dto.board.item.SpawnBoardItemDto
import com.kebuu.core.dto.board.item.TreasureBoardItemDto

class GameView(gameInfo: GameInfo) {

    val treasures = extractBoardItemsOfType<TreasureBoardItemDto>(gameInfo.board.boardItems)
    val spawns = extractBoardItemsOfType<SpawnBoardItemDto>(gameInfo.board.boardItems)
    val myLife = gameInfo.gamerLife
    val zPoints = gameInfo.gamerZPoints
    val myPosition = gameInfo.position
    val myGamerId = gameInfo.gamerId
    val mySpawnAttributes = gameInfo.spawnAttributes


    inline fun <reified T> extractBoardItemsOfType(items: List<AbstractBoardItemDto>): List<T> {
        return items.filter { it is T }.map { it as T }
    }
}