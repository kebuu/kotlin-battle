package com.kebuu.core.action.validation

import com.kebuu.core.action.*
import com.kebuu.core.action.validation.ActionValidationResult.Companion.from
import com.kebuu.core.action.validation.ActionValidationResult.Companion.ok
import com.kebuu.core.action.validation.ActionValidationResult.Companion.withError
import com.kebuu.core.game.Game
import com.kebuu.core.gamer.Gamer
import com.kebuu.core.utils.*

class ActionValidatorVisitor(val game: Game, val gamer: Gamer) : ActionValidator {

    override fun validate(digAction: DigAction): ActionValidationResult {
        return if (game.canGamerDig(gamer)) {
            ok()
        } else {
            withError("${gamer.pseudo()} n'a pas pigé les règles : il/elle creuse même quand il n'y a pas de trésor ...")
        }
    }

    override fun validate(lightSpeedMoveAction: LightSpeedMoveAction): ActionValidationResult {
        return if (game.canGamerUseLimitedAction(gamer, lightSpeedMoveAction)) {
            from(validate(lightSpeedMoveAction.moveAction))
        } else {
            withError("Hé non ${gamer.pseudo()}, tu as déjà cramé ta reserve de ${lightSpeedMoveAction.javaClass.simpleName} ! ")
        }
    }

    override fun validate(fightAction: FightAction): ActionValidationResult {
        return if (game.canGamerFight(gamer, fightAction.attackedGamerPseudo)) {
            ok()
        } else {
            withError("${fightAction.attackedGamerPseudo} est trop loin pour toi jeune padawan")
        }
    }

    override fun validate(healthAction: HealthAction): ActionValidationResult {
        return if (game.canGamerUseLimitedAction(gamer, healthAction)) {
            ok()
        } else {
            withError("${gamer.pseudo()} a cru qu'il pouvait guérir à l'infini. L'erreur de débutant(e)")
        }
    }

    override fun validate(moveAction: MoveAction): ActionValidationResult {
        return if (!game.isOnBoard(moveAction.goTo)) {
            withError("${gamer.pseudo()} a essayé de fuir le jeu ! Manque de courage ou erreur de programmation ?")
        } else if (!game.spawnOf(gamer).canMoveTo(moveAction.goTo)) {
            withError("Hey ${gamer.pseudo()} you can't go that far ! Dude !")
        } else if (game.isThereMountainOn(moveAction.goTo)) {
            withError("${gamer.pseudo()} aurait voulu aller à la montagne... Il ne bougera pas ce tour-ci")
        } else {
            ok()
        }
    }
}