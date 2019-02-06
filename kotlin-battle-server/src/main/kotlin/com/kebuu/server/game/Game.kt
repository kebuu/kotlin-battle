package com.kebuu.server.game


import com.kebuu.core.action.LimitedUseAction
import com.kebuu.core.action.LimitedUseAction.LimitedUseActionType
import com.kebuu.core.action.error.ExceptionAction
import com.kebuu.core.action.error.TimeoutAction
import com.kebuu.core.board.Board
import com.kebuu.core.board.Hole
import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.comparator.GamerActionComparator
import com.kebuu.core.dto.GameInfo
import com.kebuu.core.enums.GameStatus
import com.kebuu.core.gamer.Gamer
import com.kebuu.core.gamer.GamerAction
import com.kebuu.core.gamer.GamerSpawnAttributes
import com.kebuu.core.gamer.RemoteGamer
import com.kebuu.core.utils.Loggable
import com.kebuu.core.utils.waitAndUnwrap
import com.kebuu.server.action.visitor.ActionExecutorVisitor
import com.kebuu.server.action.visitor.ActionValidatorVisitor
import com.kebuu.server.bean.ErrorGameEvent
import com.kebuu.server.bean.GameEvent
import com.kebuu.server.bean.WarnGameEvent
import com.kebuu.server.config.GameConfig
import com.kebuu.server.enums.GameLevel
import com.kebuu.server.exception.GameAlreadyStartedException
import com.kebuu.server.service.EventLogService
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeoutException

open class Game(val config: GameConfig,
                val eventLogService: EventLogService,
                val level: GameLevel = GameLevel.LEVEL_0) : Loggable {

    var currentStep = 0
    val gamers = mutableSetOf<Gamer>()
    var status = GameStatus.CREATED
    val board = Board()
    val limitedActionUsedByGamers = mutableMapOf<Gamer, MutableMap<LimitedUseActionType, Int>>()

    fun addGamers(vararg gamers: Gamer) {
        this.gamers.addAll(gamers)
    }

    fun getGamer(gamerId: String): Gamer =
        gamers.first { it.gamerId() == gamerId }

    fun gamerExists(gamerId: String):Boolean =
        gamers.any { it.gamerId() == gamerId }

    fun getSpawn(gamerId: String): Spawn =
        board.gamerSpawn(getGamer(gamerId))

    fun init() {
        status = GameStatus.STARTED

        gamers.forEach { gamer ->
            val spawnAttributes = if (level.enableSpawnUpdate)SpawnAttributes() else SpawnAttributes(3, 0, 0, 0)
            val spawn = Spawn(gamer, spawnAttributes, board.randomEmptyPosition())
            board.addItem(spawn)
            gamer.setLife(config.gamerLife)
            gamer.setZPoints(config.initialZPoints)
        }
    }

    fun end() {
        status = GameStatus.STOPPED
    }

    fun runStep(step: Int) {
        logger.info("Running step $step")
        currentStep = step

        if (level.enableSpawnUpdate && (currentStep - 1) % config.updateSpawnInterval == 0) {
            updateSpawns(level.updateSpawnPoints)
        }

        logger.info("Getting next actions")
        val futureNextActions = mutableListOf<CompletableFuture<GamerAction>>()
        gamers.map { gamer ->
            logger.info("Getting next actions for ${gamer.shortName()}")
            futureNextActions.add(CompletableFuture.supplyAsync {getGamerAction(gamer)} )
        }

        logger.info("Processing all actions")
        processActions(futureNextActions.waitAndUnwrap())

        for (gamer in gamers) {
            val spawnPosition = board.gamerSpawn(gamer).position
            if (board.doesPositionHasItemOfType<Hole>(spawnPosition)) {
                val zPointsLost = gamer.getZPoints() * config.zPointPercentLostOnHole / 100
                gamer.loseZPoints(zPointsLost)
                board.gamerSpawn(gamer).moveTo(board.randomEmptyPosition())
                eventLogService.logEvent(GameEvent(gamer.gamerId(), currentStep,
                        "${gamer.shortName()} ne devait pas marcher bien droit, il vient de tomber dans un trou. Il perd $zPointsLost points"))
            }
        }

        for (gamer in gamers) {
            if (gamer.isDead()) {
                val gamerSpawn = board.gamerSpawn(gamer)
                val zPointsLost = gamer.getZPoints() * config.zPointPercentLostOnKill / 100
                gamer.loseZPoints(zPointsLost)
                gamerSpawn.moveTo(board.randomEmptyPosition())
                gamer.setLife(config.gamerLife)
                eventLogService.logEvent(GameEvent(gamer.gamerId(), currentStep,
                        "RIP ${gamer.shortName()} ! Il perd $zPointsLost points"))
            }
        }

        board.cleanFoundTreasures()
    }

    private fun updateSpawns(updateSpawnPoints: Int) {
        logger.info("Getting spawn update with $updateSpawnPoints points")
        val gamersSpawnAttributes = getGamersSpawnUpdate(updateSpawnPoints)

        for (gamerSpawnAttributes in gamersSpawnAttributes) {
            val gamer = gamerSpawnAttributes.gamer
            val spawn = getSpawn(gamer.gamerId())

            logger.info("Getting spawn update for ${gamer.shortName()}")
            val validationResult = spawn.validateUpdate(gamerSpawnAttributes.spawnAttributes, updateSpawnPoints)
            when {
                validationResult.isOk() -> spawn.updateAttributesTo(gamerSpawnAttributes.spawnAttributes)
                else                    -> {
                    val spawnAttributesUpdated = spawn.attributes.updateRandomly(updateSpawnPoints)
                    spawn.updateAttributesTo(spawnAttributesUpdated)
                    eventLogService.logEvent(WarnGameEvent(gamer.gamerId(), currentStep, validationResult.message))
                }
            }

            eventLogService.logEvent(GameEvent(gamer.gamerId(),  currentStep,
                    "${gamer.shortName()} a maintenant les caractéristiques suivantes : ${spawn.attributes}"))
        }
    }

    fun isOver(): Boolean {
        return currentStep >= config.maxNumberOfStep
    }

    private fun getGamerAction(gamer: Gamer): GamerAction {
        val supplyAsync: CompletableFuture<GamerAction> = CompletableFuture.supplyAsync {
            gamer.getNextAction(GameInfo(board.gamerSpawn(gamer), board, currentStep, config.maxNumberOfStep))
        }

        return try {
            supplyAsync.get(config.gamerResponseTimeout, config.gamerResponseTimeoutTimeUnit)
        } catch(e: TimeoutException) {
            supplyAsync.cancel(true)
            GamerAction(gamer, TimeoutAction())
        } catch(e: Exception) {
            e.printStackTrace()
            GamerAction(gamer, ExceptionAction(e.message.orEmpty()))
        }
    }

    private fun getGamersSpawnUpdate(updatePoints: Int): List<GamerSpawnAttributes> {
        val futureGamerSpawnAttributes = mutableListOf<CompletableFuture<GamerSpawnAttributes>>()

        gamers.forEach { gamer ->
            futureGamerSpawnAttributes.add(CompletableFuture.supplyAsync {
                getGamersSpawnUpdate(gamer, updatePoints, getSpawn(gamer.gamerId()).attributes)
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
            eventLogService.logEvent(WarnGameEvent(gamer.gamerId(), currentStep,
                    "${gamer.shortName()} n'a pas réussi à updater son pion assez vite... mise à jour aléatoire"))
            supplyAsync.cancel(true)
            GamerSpawnAttributes(gamer, currentAttributes.updateRandomly(point))
        } catch(e: Exception) {
            e.printStackTrace()
            eventLogService.logEvent(ErrorGameEvent(gamer.gamerId(), currentStep,
                    "${gamer.shortName()} a lever une exception au lieu d'updater son pion... mise à jour aléatoire"))

            GamerSpawnAttributes(gamer, currentAttributes.updateRandomly(point))
        }
    }

    private fun processActions(gamerActions: List<GamerAction>) {
        val (allowedActions, forbiddenActions) = gamerActions.partition { it.action.javaClass !in level.forbiddenActions }
        val sortedActions = allowedActions.sortedWith(GamerActionComparator)

        forbiddenActions.forEach {
            val gamerName = it.gamer.gamerId()
            eventLogService.logEvent(WarnGameEvent(gamerName,  currentStep,
                    "$gamerName a oublié que certaines action n'étaient pas autorisées à ce niveau du jeu"))
        }

        sortedActions.forEach { gamerAction ->
            if (!gamerAction.gamer.isDead()) {
                val validator = ActionValidatorVisitor(this, gamerAction.gamer)
                val executor = ActionExecutorVisitor(this, gamerAction.gamer)
                val action = gamerAction.action
                val gamerInitialPosition = getSpawn(gamerAction.gamer.gamerId()).position

                val validationResult = action.validateBy(validator)
                when {
                    validationResult.isOk() -> {
                        val message = "($gamerInitialPosition) - ${action.executeBy(executor)}"
                        eventLogService.logEvent(GameEvent(gamerAction.gamer.gamerId(), currentStep, message))
                    }
                    else                    -> {
                        val message = "($gamerInitialPosition) - ${validationResult.getValidationErrorMessages()}"
                        eventLogService.logEvent(WarnGameEvent(gamerAction.gamer.gamerId(), currentStep, message))
                    }
                }
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

    fun unregister(email: String) {
        if (isStarted()) {
            throw GameAlreadyStartedException()
        } else {
            gamers.removeAll { it.gamerId() == email }
        }
    }

    fun gamerUsedLimitedAction(gamer: Gamer, action: LimitedUseAction) {
        val limitedActionUsedForGamer = limitedActionUsedByGamers.getOrPut(gamer) { mutableMapOf() }
        val limitedActionUse = limitedActionUsedForGamer.getOrPut(action.getType()) { 0 }
        limitedActionUsedForGamer[action.getType()] = limitedActionUse + 1
    }

    fun getLimitedActionUsedBy(gamer: Gamer, actionType: LimitedUseActionType): Int =
        limitedActionUsedByGamers[gamer]?.get(actionType) ?: 0
}

