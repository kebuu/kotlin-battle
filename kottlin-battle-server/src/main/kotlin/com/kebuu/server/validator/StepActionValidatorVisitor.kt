package com.kebuu.server.validator

import com.kebuu.core.action.MoveAction
import com.kebuu.core.action.StepActionValidationResult
import com.kebuu.core.action.StepActionValidator
import com.kebuu.core.game.Game
import com.kebuu.core.gamer.Gamer
import com.kebuu.server.utils.canMoveTo
import com.kebuu.server.utils.isOnBoard
import com.kebuu.server.utils.isThereMountainOn
import com.kebuu.server.utils.spawnOf

class StepActionValidatorVisitor(val game: Game, val gamer: Gamer) : StepActionValidator {

    override fun validate(moveAction: MoveAction): StepActionValidationResult {
        var result = StepActionValidationResult.ok()

        if (!game.isOnBoard(moveAction.goTo)) {
            result.addErrorMessage("${gamer.pseudo()} a essayé de fuir le jeu ! Manque de courage ou erreur de programmation ?")
        } else if (!game.spawnOf(gamer).canMoveTo(moveAction.goTo)) {
            result.addErrorMessage("Hey ${gamer.pseudo()} you can't go that far ! Dude !")
        } else if (game.isThereMountainOn(moveAction.goTo)) {
            result.addErrorMessage("${gamer.pseudo()} aurait voulu aller à la montagne... Il ne bougera pas ce tour-ci")
        }

        return result
    }
}