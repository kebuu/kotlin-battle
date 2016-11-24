package com.kebuu.server.gamer.bot

import com.kebuu.core.Dimension
import com.kebuu.core.action.DigAction
import com.kebuu.core.action.MoveAction
import com.kebuu.core.action.NoAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.bot.Bot
import com.kebuu.core.dto.GameInfo
import com.kebuu.core.dto.board.item.TreasureBoardItemDto
import com.kebuu.core.gamer.BaseGamer
import com.kebuu.server.config.GameConfig

class GreedyBot private constructor(gamerId: String, override val type: String): BaseGamer(gamerId), Bot {

    constructor(): this("GreedyBot-" + Bot.COUNTER.andIncrement, "greedyBot")

    var spawnAttributes = SpawnAttributes()
    lateinit var dimension: Dimension

    override fun doGetNextAction(gameInfo: GameInfo): StepAction {
        if(gameInfo.board.dimension != null) {
            dimension = gameInfo.board.dimension!!
        }

        var action: StepAction = NoAction()

        spawnAttributes = gameInfo.spawnAttributes

        val itemsAt = gameInfo.board.boardItems.groupBy { it.position }

        val treasures = gameInfo.board.boardItems
                .filter { it.position == gameInfo.position || itemsAt[it.position]?.size == 1 }
                .filter { it is TreasureBoardItemDto }

        if(treasures.isNotEmpty()) {
            if(treasures.any { it.position == gameInfo.position }){
                action = DigAction()
            } else {
                val accessibleTreasures = treasures.filter {
                    gameInfo.position.distanceFrom(it.position) <= spawnAttributes.speed
                }

                if(accessibleTreasures.isNotEmpty()) {
                    action = MoveAction(accessibleTreasures.first().position)
                } else {
                    val closestTreasure = treasures.minBy { it.position.distanceFrom(gameInfo.position) }!!
                    val closestAccessiblePosition = dimension.filter { gameInfo.position.distanceFrom(it) <= spawnAttributes.speed }
                            .minBy { it.distanceFrom(closestTreasure.position) }!!
                    action = MoveAction(closestAccessiblePosition)
                }
            }
        }

        return action
    }

    override fun doGetSpawnAttributes(point: Int): SpawnAttributes {
        spawnAttributes.speed += point
        return spawnAttributes
    }
}

