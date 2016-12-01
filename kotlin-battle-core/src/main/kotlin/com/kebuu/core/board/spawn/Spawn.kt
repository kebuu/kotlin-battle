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
            SpawnUpdateValidationResult.withError("${owner.shortName()}, la valeur des attributs d'un pion ne peut pas être négatif")
        } else if (newSpawnAttributes.sumPoints - attributes.sumPoints > spawnAttributesUpdatePoints) {
            SpawnUpdateValidationResult.withError("${owner.shortName()} a essayé de booster son pion un peu plus que prévu")
        }else if (newSpawnAttributes.force < attributes.force ||
                newSpawnAttributes.speed < attributes.speed ||
                newSpawnAttributes.resistance < attributes.resistance ||
                newSpawnAttributes.shootDistance < attributes.shootDistance) {
            SpawnUpdateValidationResult.withError("Non, non, non${owner.shortName()} on ne peut pas mettre son pion à jour n'importe comment")
        } else {
            SpawnUpdateValidationResult.ok()
        }
    }

    fun updateAttributesTo(newSpawnAttributes: SpawnAttributes) {
        attributes = newSpawnAttributes
    }
}