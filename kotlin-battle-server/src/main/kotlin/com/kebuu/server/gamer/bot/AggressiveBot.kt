package com.kebuu.server.gamer.bot

import com.kebuu.core.Dimension
import com.kebuu.core.action.FightAction
import com.kebuu.core.action.MoveAction
import com.kebuu.core.action.NoAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.bot.Bot
import com.kebuu.core.dto.GameInfo
import com.kebuu.core.dto.board.item.MountainBoardItemDto
import com.kebuu.core.dto.board.item.SpawnBoardItemDto
import com.kebuu.core.gamer.BaseGamer

class AggressiveBot private constructor(gamerId: String, override val type: String) : BaseGamer(gamerId), Bot {

    constructor() : this("AggressiveBot-" + Bot.COUNTER.andIncrement, "aggressiveBot")

    var spawnAttributes = SpawnAttributes()
    lateinit var dimension: Dimension

    override fun doGetNextAction(gameInfo: GameInfo): StepAction {
        if (gameInfo.board.dimension != null) {
            dimension = gameInfo.board.dimension!!
        }

        spawnAttributes = gameInfo.spawnAttributes
        val itemsAt = gameInfo.board.boardItems.groupBy { it.position }

        val enemies = gameInfo.board.boardItems
                .filter { it is SpawnBoardItemDto && it.gamerId != gameInfo.gamerId }
                .map { it as SpawnBoardItemDto }

        val closestEnemy: SpawnBoardItemDto? = enemies.minBy { it.position.distanceFrom(gameInfo.position) }

        return closestEnemy?.let { enemy ->
            when {
                enemy.position.distanceFrom(gameInfo.position) <= spawnAttributes.shootDistance -> FightAction(enemy.gamerId)
                else                                                                            -> {
                    val closestAccessiblePosition = dimension
                            .filter {
                                gameInfo.position.distanceFrom(it) <= spawnAttributes.speed
                            }
                            .filter { position ->
                                val items = itemsAt[position] ?: listOf()
                                items.none { it is MountainBoardItemDto }
                            }
                            .minBy { it.distanceFrom(enemy.position) }!!
                    MoveAction(closestAccessiblePosition)
                }
            }
        } ?: NoAction()
    }

    override fun doGetSpawnAttributes(point: Int): SpawnAttributes {
        val thirdPoint = Math.floor(point.toDouble() / 3.toDouble()).toInt()
        spawnAttributes.force += thirdPoint
        spawnAttributes.shootDistance += thirdPoint
        spawnAttributes.speed += (point - 2 * thirdPoint)
        return spawnAttributes
    }
}

