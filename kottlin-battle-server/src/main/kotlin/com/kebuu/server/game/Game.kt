package com.kebuu.server.game


import com.kebuu.core.Dimension
import com.kebuu.core.Position
import com.kebuu.core.action.LimitedUseAction.LimitedUseActionType
import com.kebuu.core.action.error.ExceptionAction
import com.kebuu.core.action.error.TimeoutAction
import com.kebuu.core.board.Board
import com.kebuu.core.board.Hole
import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.comparator.GamerActionComparator
import com.kebuu.core.dto.GameInfo
import com.kebuu.core.enums.GameLevel
import com.kebuu.core.enums.GameStatus
import com.kebuu.core.gamer.Gamer
import com.kebuu.core.gamer.GamerAction
import com.kebuu.core.gamer.GamerSpawnAttributes
import com.kebuu.core.gamer.RemoteGamer
import com.kebuu.core.utils.waitAndUnwrap
import com.kebuu.server.action.visitor.ActionExecutorVisitor
import com.kebuu.server.action.visitor.ActionValidatorVisitor
import com.kebuu.server.bean.GameEvent
import com.kebuu.server.config.GameConfig
import com.kebuu.server.exception.GameAlreadyStartedException
import com.kebuu.server.service.EventLogService
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeoutException

open class Game(val id: Int,
                val config: GameConfig,
                val eventLogService: EventLogService,
                val level: GameLevel = GameLevel.LEVEL_0) {

    var currentStep = 0
    val gamers: MutableList<Gamer> = mutableListOf()
    var status: GameStatus = GameStatus.CREATED
    val board: Board = Board()
    val random: Random = Random(1)
    val limitedActionUsedByGamers = mapOf<Gamer, Map<LimitedUseActionType, Int>>()

    fun addGamers(vararg gamers: Gamer) {
        this.gamers.addAll(gamers)
    }

    fun getGamer(pseudo: String): Gamer {
        return gamers.first { it.pseudo() == pseudo}
    }

    fun getSpawn(pseudo: String): Spawn {
        return board.gamerSpawn(getGamer(pseudo))
    }

    fun init() {
        status = GameStatus.STARTED
        board.setDimension(Dimension(5, 5))

        val gamersSpawnAttributes = getInitialSpawnAttributes()
        gamersSpawnAttributes
                .map { Spawn(it.gamer, it.spawnAttributes, Position.Companion.ORIGIN) }
                .forEach { board.addItem(it) }
    }

    fun end() {
        status = GameStatus.STOPPED
    }

    fun runStep(step: Int) {
        currentStep = step

        if (level.enableSpawnUpdate && currentStep % config.updateSpawnInterval == 0) {
            val spawnAttributesUpdatePoints = level.spawnUpdatePoints
            val gamersSpawnAttributes = getGamersSpawnUpdate(spawnAttributesUpdatePoints)

            for (gamerSpawnAttributes in gamersSpawnAttributes) {
                val gamer = gamerSpawnAttributes.gamer
                val spawn = getSpawn(gamer.pseudo())

                val validationResult = spawn.validateUpdate(gamerSpawnAttributes.spawnAttributes, spawnAttributesUpdatePoints)
                if (validationResult.isOk()) {
                    spawn.updateAttributesTo(gamerSpawnAttributes.spawnAttributes)
                } else {
                    spawn.attributes.updateRandomly(spawnAttributesUpdatePoints)
                    eventLogService.logEvent(GameEvent(gamer.pseudo(),
                            "${gamer.pseudo()} ne sait pas faire des additions correctement pour configurer son pion -> " +
                                    "mise à jour aléatoire"))
                }
            }
        }

        val futureNextActions = mutableListOf<CompletableFuture<GamerAction>>()
        gamers.map { gamer ->
            futureNextActions.add(CompletableFuture.supplyAsync {getGamerAction(gamer)} )
        }

        processActions(futureNextActions.waitAndUnwrap())

        for (gamer in gamers) {
            val spawnPosition = board.gamerSpawn(gamer).position
            if (board.doesPositionHasItemOfType<Hole>(spawnPosition)) {
                board.gamerSpawn(gamer).moveTo(board.randomEmptyPosition())
                eventLogService.logEvent(GameEvent(gamer.pseudo(),
                        "${gamer.pseudo()} ne devait pas marcher bien droit, il vient de tomber dans un trou"))
            }
        }

        for (gamer in gamers) {
            if (gamer.isDead()) {
                val gamerSpawn = board.gamerSpawn(gamer)
                gamerSpawn.moveTo(board.randomEmptyPosition())
                gamer.setLife(gamerSpawn.attributes.resistance)
                eventLogService.logEvent(GameEvent(gamer.pseudo(),
                        "RIP ${gamer.pseudo()} !"))
            }
        }
    }

    fun isOver(): Boolean {
        return currentStep >= config.maxNumberOfStep
    }

    private fun getGamerAction(gamer: Gamer): GamerAction {
        val supplyAsync: CompletableFuture<GamerAction> = CompletableFuture.supplyAsync {
            gamer.getNextAction(GameInfo(board.gamerSpawn(gamer), board))
        }

        return try {
            supplyAsync.get(config.gamerResponseTimeout, config.gamerResponseTimeoutTimeUnit)
        } catch(e: TimeoutException) {
            supplyAsync.cancel(true)
            GamerAction(gamer, TimeoutAction())
        } catch(e: Exception) {
            GamerAction(gamer, ExceptionAction(e.message.orEmpty()))
        }
    }

    private fun getInitialSpawnAttributes(): List<GamerSpawnAttributes> {
        return if (level.enableSpawnUpdate) {
            getGamersSpawnUpdate(0)
        } else {
            gamers.map { GamerSpawnAttributes(it, SpawnAttributes()) }
        }
    }

    private fun getGamersSpawnUpdate(updatePoints: Int): List<GamerSpawnAttributes> {
        val futureGamerSpawnAttributes = mutableListOf<CompletableFuture<GamerSpawnAttributes>>()

        gamers.map { gamer ->
            futureGamerSpawnAttributes.add(CompletableFuture.supplyAsync {
                getGamersSpawnUpdate(gamer, updatePoints, SpawnAttributes())
            })
        }

        return futureGamerSpawnAttributes.waitAndUnwrap()
    }

    private fun getGamersSpawnUpdate(gamer: Gamer, point: Int, currentAttributes: SpawnAttributes): GamerSpawnAttributes {
        val supplyAsync: CompletableFuture<GamerSpawnAttributes> = CompletableFuture.supplyAsync {
            gamer.getSpawnAttributes(point)
        }

        return try {
            supplyAsync.get(config.gamerResponseTimeout, config.gamerResponseTimeoutTimeUnit)
        } catch(e: TimeoutException) {
            supplyAsync.cancel(true)
            GamerSpawnAttributes(gamer, currentAttributes.updateRandomly(point))
        } catch(e: Exception) {
            GamerSpawnAttributes(gamer, currentAttributes.updateRandomly(point))
        }
    }

    private fun processActions(gamerActions: List<GamerAction>) {
        val sortedActions = gamerActions.sortedWith(GamerActionComparator)

        for (gamerAction in sortedActions) {
            val validator = ActionValidatorVisitor(this, gamerAction.gamer)
            val executor = ActionExecutorVisitor(this, gamerAction.gamer)
            val action = gamerAction.action

            val validationResult = action.validateBy(validator)
            if (validationResult.isOk()) {
                val executionMessage = action.executeBy(executor)
                eventLogService.logEvent(GameEvent(gamerAction.gamer.pseudo(), executionMessage))
            }
        }
    }

    fun isStarted() = status == GameStatus.STARTED

    fun register(remoteGamer: RemoteGamer) {
        if (isStarted()) {
            throw GameAlreadyStartedException()
        } else {
            gamers.add(remoteGamer)
        }
    }

    fun unregister(name: String) {
        if (isStarted()) {
            throw GameAlreadyStartedException()
        } else {
            gamers.removeAll { it.pseudo() == name }
        }
    }
}

