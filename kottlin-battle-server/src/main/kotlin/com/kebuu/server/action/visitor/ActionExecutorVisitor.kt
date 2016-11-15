package com.kebuu.server.action.visitor

import com.kebuu.core.action.*
import com.kebuu.core.action.error.ExceptionAction
import com.kebuu.core.action.error.TimeoutAction
import com.kebuu.core.action.execution.ActionExecutor
import com.kebuu.core.gamer.Gamer
import com.kebuu.server.game.Game

class ActionExecutorVisitor(val game: Game, val gamer: Gamer) : ActionExecutor {

    private val board = game.board
    
    override fun execute(noAction: NoAction) = "${gamer.pseudo()} a eu peur, il n'a rien fait ce tour-ci !"

    override fun execute(digAction: DigAction): String {
        val gamerSpawn = game.getSpawn(gamer.pseudo())
        val treasure = board.getTreasureAt(gamerSpawn.position)!!
        gamer.addZPoints(treasure.zPoints.toDouble())
        treasure.discoveredAtStep = game.currentStep
        return "${gamer.pseudo()} a deterré ${treasure.zPoints} zPoints !!"
    }

    override fun execute(fightAction: FightAction): String {
        val gamerSpawn = game.getSpawn(gamer.pseudo())
        val defenderSpawn = game.getSpawn(fightAction.attackedGamerPseudo)
        val defender = defenderSpawn.owner

        val effectiveAttackForce = Math.max(gamerSpawn.attributes.force - defenderSpawn.attributes.resistance, 1)
        defender.removeLife(Math.max(effectiveAttackForce, 0))
        return "${gamer.pseudo()} inflige $effectiveAttackForce point(s) de dégat à $fightAction.attackedGamerPseudo"
    }

    override fun execute(healthAction: HealthAction): String {
        val gamerSpawn = game.getSpawn(gamer.pseudo())
        gamer.setLife(gamerSpawn.attributes.resistance)
        return "${gamer.pseudo()} récupère toute sa vie"
    }

    override fun execute(lightSpeedMoveAction: LightSpeedMoveAction): String {
        return execute(lightSpeedMoveAction.moveAction) + " a la vitesse de la lumière"
    }

    override fun execute(moveAction: MoveAction): String {
        board.gamerSpawn(gamer).moveTo(moveAction.goTo)
        return "${gamer.pseudo()} se déplace en ${moveAction.goTo.x}-${moveAction.goTo.y}"
    }

    override fun execute(exceptionAction: ExceptionAction) = 
        "/!\\ ${gamer.pseudo()} ne fera rien ce tour-ci a cause d'une exception : ${exceptionAction.message}"

    override fun execute(timeoutAction: TimeoutAction) = "/!\\ ${gamer.pseudo()} a été trop lent pour pouvoir jouer dans ce tour"
}