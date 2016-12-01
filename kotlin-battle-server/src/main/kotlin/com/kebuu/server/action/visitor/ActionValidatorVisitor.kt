package com.kebuu.server.action.visitor

import com.kebuu.core.action.*
import com.kebuu.core.action.validation.ActionValidationResult
import com.kebuu.core.action.validation.ActionValidationResult.Companion.ok
import com.kebuu.core.action.validation.ActionValidationResult.Companion.resultFrom
import com.kebuu.core.action.validation.ActionValidationResult.Companion.withError
import com.kebuu.core.action.validation.ActionValidator
import com.kebuu.core.gamer.Gamer
import com.kebuu.core.utils.canMoveTo
import com.kebuu.server.game.Game
import com.kebuu.server.utils.*

class ActionValidatorVisitor(val game: Game, val gamer: Gamer) : ActionValidator {

    override fun validate(digAction: DigAction): ActionValidationResult {
        return if (game.canGamerDig(gamer)) {
            ok()
        } else {
            withError("${gamer.shortName()} n'a pas pigé les règles : il/elle creuse même quand il n'y a pas de trésor ...")
        }
    }

    override fun validate(lightSpeedMoveAction: LightSpeedMoveAction): ActionValidationResult {
        return if (game.canGamerUseLimitedAction(gamer, lightSpeedMoveAction)) {
            resultFrom(validateMove(lightSpeedMoveAction.moveAction))
        } else {
            withError("Hé non ${gamer.shortName()}, tu as déjà cramé ta reserve de ${lightSpeedMoveAction.javaClass.simpleName} ! ")
        }
    }

    override fun validate(fightAction: FightAction): ActionValidationResult {
        return if (!game.gamerExists(fightAction.attackedGamerId)) {
            withError("${gamer.shortName()} a essayé de s'attaquer à un joueur fantôme : ${fightAction.attackedGamerId}")
        } else if (gamer.gamerId() == fightAction.attackedGamerId) {
            withError("${gamer.shortName()} touche le fond, il cherche à s'attaquer lui-même")
        } else if (game.canGamerFight(gamer, fightAction.attackedGamerId)) {
            ok()
        } else {
            withError("Hey, ${gamer.shortName()}, ${fightAction.attackedGamerId} est trop loin pour toi !")
        }
    }

    override fun validate(healAction: HealAction): ActionValidationResult {
        return if (game.canGamerUseLimitedAction(gamer, healAction)) {
            ok()
        } else {
            withError("${gamer.shortName()} a cru qu'il pouvait guérir à l'infini. L'erreur de débutant(e)")
        }
    }

    override fun validate(moveAction: MoveAction): ActionValidationResult {
        return if (!game.spawnOf(gamer).canMoveTo(moveAction.goTo)) {
            withError("Hey ${gamer.shortName()} you can't go that far ! Dude !")
        } else {
            resultFrom(validateMove(moveAction))
        }
    }

    fun validateMove(moveAction: MoveAction): ActionValidationResult {
        return if (!game.isOnBoard(moveAction.goTo)) {
            withError("${gamer.shortName()} a essayé de fuir le jeu ! Manque de courage ou erreur de programmation ?")
        } else if (game.isThereMountainOn(moveAction.goTo)) {
            withError("${gamer.shortName()} aurait voulu aller à la montagne... Il ne bougera pas ce tour-ci")
        } else {
            ok()
        }
    }
}