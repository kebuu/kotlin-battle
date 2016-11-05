package com.kebuu.core.utils

import com.kebuu.core.Position
import com.kebuu.core.action.LimitedUseAction
import com.kebuu.core.board.Mountain
import com.kebuu.core.game.Game
import com.kebuu.core.gamer.Gamer

fun Game.isOnBoard(position: Position) = this.board.isOnBoard(position)

fun Game.spawnOf(gamer: Gamer) = this.board.gamerSpawn(gamer)

fun Game.isThereMountainOn(position: Position): Boolean {
    return this.board.itemsAt(position).any { it is Mountain }
}

fun Game.canGamerUseLimitedAction(gamer: Gamer, action: LimitedUseAction): Boolean {
    val actionUsedOccurrence = limitedActionUsedByGamers[gamer]?.get(action.getType()) ?: 0
    val allowedActionOccurrence = config.limitedActionAllowedOccurrence[action.getType()]
    return allowedActionOccurrence == null || actionUsedOccurrence < allowedActionOccurrence
}

fun Game.canGamerFight(gamer: Gamer, fightTargetPseudo: String): Boolean {
    val attackerSpawn = getSpawn(gamer.pseudo())
    val attackerPosition = attackerSpawn.position
    val defenderPosition = getSpawn(fightTargetPseudo).position
    return attackerPosition.distanceFrom(defenderPosition) <= attackerSpawn.attributes.shootDistance
}

fun Game.canGamerDig(gamer: Gamer): Boolean {
    val gamerSpawn = getSpawn(gamer.pseudo())
    val treasure = board.getTreasureAt(gamerSpawn.position)
    return if (treasure == null || treasure.hasBeenDiscoveredBeforeStep(currentStep)) {
        false
    } else {
        true
    }
}