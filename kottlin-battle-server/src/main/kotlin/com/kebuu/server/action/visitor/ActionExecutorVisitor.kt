package com.kebuu.server.action.visitor

import com.kebuu.core.action.*
import com.kebuu.core.action.error.ExceptionAction
import com.kebuu.core.action.error.TimeoutAction
import com.kebuu.core.action.execution.ActionExecutor
import com.kebuu.core.gamer.Gamer
import com.kebuu.server.game.Game

class ActionExecutorVisitor(val game: Game, val gamer: Gamer) : ActionExecutor {

    private val board = game.board
    
    override fun execute(noAction: NoAction) = "${gamer.shortName()} a eu peur, il n'a rien fait ce tour-ci !"

    override fun execute(digAction: DigAction): String {
        val gamerSpawn = game.getSpawn(gamer.gamerId())
        val treasure = board.getTreasureAt(gamerSpawn.position)!!
        gamer.gainZPoints(treasure.zPoints)
        treasure.hasBeenFound = true
        return "${gamer.shortName()} a deterré ${treasure.zPoints} zPoints !!"
    }

    override fun execute(fightAction: FightAction): String {
        val gamerSpawn = game.getSpawn(gamer.gamerId())
        val defenderSpawn = game.getSpawn(fightAction.attackedGamerId)
        val defender = defenderSpawn.owner

        val effectiveAttackForce = Math.max(gamerSpawn.attributes.force - defenderSpawn.attributes.resistance, 1)
        defender.loseLife(effectiveAttackForce)
        val zPointTakenOnKill = defender.getZPoints() * game.config.zPointPercentTakenOnKill / 100

        if (defender.isDead()) {
            gamer.gainZPoints(zPointTakenOnKill)
        }

        return "${gamer.shortName()} inflige $effectiveAttackForce point(s) de dégat à ${defender.shortName()}"
    }

    override fun execute(healAction: HealAction): String {
        val gamerSpawn = game.getSpawn(gamer.gamerId())
        gamer.setLife(gamerSpawn.attributes.resistance)
        game.gamerUsedLimitedAction(gamer, healAction)
        return "${gamer.shortName()} récupère toute sa vie"
    }

    override fun execute(lightSpeedMoveAction: LightSpeedMoveAction): String {
        game.gamerUsedLimitedAction(gamer, lightSpeedMoveAction)
        return execute(lightSpeedMoveAction.moveAction) + " a la vitesse de la lumière"
    }

    override fun execute(moveAction: MoveAction): String {
        board.gamerSpawn(gamer).moveTo(moveAction.goTo)
        return "${gamer.shortName()} se déplace en ${moveAction.goTo.x}-${moveAction.goTo.y}"
    }

    override fun execute(exceptionAction: ExceptionAction) = 
        "/!\\ ${gamer.shortName()} ne fera rien ce tour-ci a cause d'une exception : ${exceptionAction.message}"

    override fun execute(timeoutAction: TimeoutAction) = "/!\\\\ ${gamer.shortName()} a été trop lent pour pouvoir jouer dans ce tour"
}