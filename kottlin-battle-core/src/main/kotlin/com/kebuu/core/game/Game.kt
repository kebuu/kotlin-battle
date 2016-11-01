package com.kebuu.core.game


import com.kebuu.core.Dimension
import com.kebuu.core.Position
import com.kebuu.core.action.ExceptionAction
import com.kebuu.core.action.MoveAction
import com.kebuu.core.action.TimeoutAction
import com.kebuu.core.board.Board
import com.kebuu.core.board.Hole
import com.kebuu.core.board.Mountain
import com.kebuu.core.board.ZPoint
import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.board.spawn.SpawnAttributes
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
                val gameConfig: GameConfig,
                val mode: GameMode = GameMode.LEVEL_0) {

    var currentStep = 0
    val gamers: MutableList<Gamer> = mutableListOf()
    var status: GameStatus = GameStatus.CREATED
    val board: Board = Board()

    fun addGamers(vararg gamers: Gamer) {
        this.gamers.addAll(gamers)
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
        board.addItem(ZPoint(board.randomEmptyPosition()))
    }

    fun end() {
        status = GameStatus.STOPPED
    }

    fun runStep(step: Int) {
        currentStep = step

        val futureNextActions = mutableListOf<CompletableFuture<GamerAction>>()

        gamers.map { gamer ->
            futureNextActions.add(CompletableFuture.supplyAsync {getGamerAction(gamer)} )
        }

        processActions(futureNextActions.waitAndUnwrap())
    }

    private fun getGamerAction(gamer: Gamer): GamerAction {
        val supplyAsync: CompletableFuture<GamerAction> = CompletableFuture.supplyAsync {
            gamer.getNextAction(GameInfo(board.gamerSpawn(gamer), this))
        }

        return try {
            supplyAsync.get(gameConfig.gamerResponseTimeout, gameConfig.gamerResponseTimeoutTimeUnit)
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
            supplyAsync.get(gameConfig.gamerResponseTimeout, gameConfig.gamerResponseTimeoutTimeUnit)
        } catch(e: TimeoutException) {
            supplyAsync.cancel(true)
            GamerSpawnAttributes(gamer, currentAttributes.updateRandomly(point))
        } catch(e: Exception) {
            GamerSpawnAttributes(gamer, currentAttributes.updateRandomly(point))
        }
    }

    private fun processActions(gamerActions: List<GamerAction>) {
        for (gamerAction in gamerActions) {
            val action = gamerAction.action
            if (action is MoveAction) {
                board.gamerSpawn(gamerAction.gamer).moveTo(action.goTo)
            }
        }
    }

    fun isOver(): Boolean {
        return currentStep >= gameConfig.maxNumberOfStep
    }
}
