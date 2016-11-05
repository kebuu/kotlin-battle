package com.kebuu.core.board.spawn

import com.kebuu.core.Position
import com.kebuu.core.action.validation.SpawnUpdateValidationResult
import com.kebuu.core.board.AbstractBoardItem
import com.kebuu.core.gamer.Gamer

class Spawn(val owner: Gamer,
            var attributes: SpawnAttributes = SpawnAttributes(),
            position: Position = Position.ORIGIN): AbstractBoardItem(position) {

    fun moveTo(position: Position) {
        this.position = position
    }

    fun validateUpdate(newSpawnAttributes: SpawnAttributes, spawnAttributesUpdatePoints: Int): SpawnUpdateValidationResult {
        return if (!newSpawnAttributes.isValid()) {
            SpawnUpdateValidationResult.withError("") //TODO
        } else if (newSpawnAttributes.sumPoints - attributes.sumPoints > spawnAttributesUpdatePoints) {
            SpawnUpdateValidationResult.withError("") //TODO
        } else {
            SpawnUpdateValidationResult.ok()
        }
    }

    fun updateAttributesTo(newSpawnAttributes: SpawnAttributes) {
        attributes = newSpawnAttributes
    }
}