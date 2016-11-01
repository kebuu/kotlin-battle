package com.kebuu.server.utils

import com.kebuu.core.Position
import com.kebuu.core.board.Mountain
import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.game.Game
import com.kebuu.core.gamer.Gamer

fun Game.isOnBoard(position: Position) = this.board.isOnBoard(position)

fun Game.spawnOf(gamer: Gamer) = this.board.gamerSpawn(gamer)

fun Game.isThereMountainOn(position: Position): Boolean {
    return this.board.items.any { it is Mountain }
}

fun Spawn.canMoveTo(position: Position): Boolean {
    return this.position.distanceFrom(position) <= this.attributes.speed
}

operator fun Position.plus(position: Position): Position {
    return this.plusX(position.x).plusY(position.y)
}