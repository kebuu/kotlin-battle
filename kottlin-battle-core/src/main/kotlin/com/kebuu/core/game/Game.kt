package com.kebuu.core.game


import com.kebuu.core.Dimension
import com.kebuu.core.Position
import com.kebuu.core.action.LimitedUseAction.LimitedUseActionType
import com.kebuu.core.action.error.ExceptionAction
import com.kebuu.core.action.error.TimeoutAction
import com.kebuu.core.action.execution.ActionExecutorVisitor
import com.kebuu.core.action.validation.ActionValidatorVisitor
import com.kebuu.core.board.Board
import com.kebuu.core.board.Hole
import com.kebuu.core.board.Mountain
import com.kebuu.core.board.Treasure
import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.comparator.GamerActionComparator
import com.kebuu.core.config.GameConfig
import com.kebuu.core.dto.GameInfo
import com.kebuu.core.enums.GameMode
import com.kebuu.core.enums.GameStatus
import com.kebuu.core.gamer.Gamer
import com.kebuu.core.gamer.GamerAction
import com.kebuu.core.gamer.GamerSpawnAttributes
import com.kebuu.core.utils.waitAndUnwrap
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeoutException

open class Game(val id: Int,
                val config: GameConfig,
                val mode: GameMode = GameMode.LEVEL_0) {

    var currentStep = 0
    val gamers: MutableList<Gamer> = mutableListOf()
    var status: GameStatus = GameStatus.CREATED
    val board: Board = Board()
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

        val futureGamerSpawnAttributes = mutableListOf<CompletableFuture<GamerSpawnAttributes>>()

        gamers.map { gamer ->
            futureGamerSpawnAttributes.add(CompletableFuture.supplyAsync {
                getSpawnAttributes(gamer, 0, SpawnAttributes())
            })
        }

        val gamersSpawnAttributes = futureGamerSpawnAttributes.waitAndUnwrap()
        for (gamerSpawnAttributes in gamersSpawnAttributes) {
            val spawn = Spawn(gamerSpawnAttributes.gamer, gamerSpawnAttributes.spawnAttributes, Position.ORIGIN)
            board.addItem(spawn)
        }


        // TODO remove
        board.addItem(Hole(board.randomEmptyPosition()))
        board.addItem(Mountain(board.randomEmptyPosition()))
        board.addItem(Treasure(board.randomEmptyPosition()))
    }

    fun end() {
        status = GameStatus.STOPPED
    }

    fun runStep(step: Int) {
        currentStep = step

        if (currentStep % 15 == 0) {
            // TODO Attention code duplique avec init()
            val spawnAttributesUpdatePoints = 0
            val futureGamerSpawnAttributes = mutableListOf<CompletableFuture<GamerSpawnAttributes>>()

            gamers.map { gamer ->
                futureGamerSpawnAttributes.add(CompletableFuture.supplyAsync {
                    getSpawnAttributes(gamer, spawnAttributesUpdatePoints, SpawnAttributes())
                })
            }

            val gamersSpawnAttributes = futureGamerSpawnAttributes.waitAndUnwrap()
            for (gamerSpawnAttributes in gamersSpawnAttributes) {
                val spawn = getSpawn(gamerSpawnAttributes.gamer.pseudo())

                val validationResult = spawn.validateUpdate(gamerSpawnAttributes.spawnAttributes, spawnAttributesUpdatePoints)
                if (validationResult.isOk()) {
                    spawn.updateAttributesTo(gamerSpawnAttributes.spawnAttributes)
                } else {
                    // TODO
                }

            }

        }

        val futureNextActions = mutableListOf<CompletableFuture<GamerAction>>()
        gamers.map { gamer ->
            futureNextActions.add(CompletableFuture.supplyAsync {getGamerAction(gamer)} )
        }
        processActions(futureNextActions.waitAndUnwrap())
    }

    fun isOver(): Boolean {
        return currentStep >= config.maxNumberOfStep
    }

    private fun getGamerAction(gamer: Gamer): GamerAction {
        val supplyAsync: CompletableFuture<GamerAction> = CompletableFuture.supplyAsync {
            gamer.getNextAction(GameInfo(board.gamerSpawn(gamer), this))
        }

        return try {
            supplyAsync.get(config.gamerResponseTimeout, config.gamerResponseTimeoutTimeUnit)
        } catch(e: TimeoutException) {
            supplyAsync.cancel(true)
            GamerAction(gamer, TimeoutAction())
        } catch(e: Exception) {
            GamerAction(gamer, ExceptionAction())
        }
    }

    private fun getSpawnAttributes(gamer: Gamer, point: Int, currentAttributes: SpawnAttributes): GamerSpawnAttributes {
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
            }
        }
    }
}
