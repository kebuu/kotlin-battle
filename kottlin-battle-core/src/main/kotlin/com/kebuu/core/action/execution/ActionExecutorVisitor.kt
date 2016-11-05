package com.kebuu.core.action.execution

import com.kebuu.core.action.*
import com.kebuu.core.game.Game
import com.kebuu.core.gamer.Gamer

class ActionExecutorVisitor(val game: Game, val gamer: Gamer) : ActionExecutor {

    override fun execute(noAction: NoAction) = String()

    override fun execute(digAction: DigAction): String {
        val gamerSpawn = game.getSpawn(gamer.pseudo())
        val treasure = game.board.getTreasureAt(gamerSpawn.position)!!
        gamer.addZPoints(treasure.zPoints)
        treasure.discoveredAtStep = game.currentStep
        return String()
    }

    override fun execute(fightAction: FightAction): String {
        val gamerSpawn = game.getSpawn(gamer.pseudo())
        val defenderSpawn = game.getSpawn(fightAction.attackedGamerPseudo)
        val defender = defenderSpawn.owner

        val effectiveAttackForce = gamerSpawn.attributes.force - defenderSpawn.attributes.resistance
        defender.removeLife(Math.max(effectiveAttackForce.toDouble(), 0.0))
        return String()
    }

    override fun execute(healthAction: HealthAction): String {
        val gamerSpawn = game.getSpawn(gamer.pseudo())
        gamer.setLife(gamerSpawn.attributes.resistance.toDouble())
        return String()
    }

    override fun execute(lightSpeedMoveAction: LightSpeedMoveAction): String {
        return execute(lightSpeedMoveAction.moveAction)
    }

    override fun execute(moveAction: MoveAction): String {
        game.board.gamerSpawn(gamer).moveTo(moveAction.goTo)
        return String()
    }
}