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

class GreedyBot constructor(
        gamerId: String = "GreedyBot-${Bot.COUNTER.andIncrement}",
        override val type: String = "greedyBot"
) : BaseGamer(gamerId), Bot {

    var spawnAttributes = SpawnAttributes()
    lateinit var dimension: Dimension

    override fun doGetNextAction(gameInfo: GameInfo): StepAction {
        if (gameInfo.board.dimension != null) {
            dimension = gameInfo.board.dimension!!
        }

        spawnAttributes = gameInfo.spawnAttributes

        val itemsAt = gameInfo.board.boardItems.groupBy { it.position }

        val treasures = gameInfo.board.boardItems
                .filter { it.position == gameInfo.position || itemsAt[it.position]?.size == 1 }
                .filter { it is TreasureBoardItemDto }

        return if (treasures.isNotEmpty()) {
            val botIsOnTreasure = treasures.any { it.position == gameInfo.position }
            when {
                botIsOnTreasure -> DigAction()
                else            -> {
                    val accessibleTreasures = treasures.filter {
                        gameInfo.position.distanceFrom(it.position) <= spawnAttributes.speed
                    }

                    if (accessibleTreasures.isNotEmpty()) {
                        MoveAction(accessibleTreasures.first().position)
                    } else {
                        val closestTreasure = treasures.minBy { it.position.distanceFrom(gameInfo.position) }!!
                        val closestAccessiblePosition = dimension
                                .filter { gameInfo.position.distanceFrom(it) <= spawnAttributes.speed }
                                .minBy { it.distanceFrom(closestTreasure.position) }!!
                        MoveAction(closestAccessiblePosition)
                    }
                }
            }
        } else {
            NoAction()
        }
    }

    override fun doGetSpawnAttributes(point: Int): SpawnAttributes {
        spawnAttributes.speed += point
        return spawnAttributes
    }
}

