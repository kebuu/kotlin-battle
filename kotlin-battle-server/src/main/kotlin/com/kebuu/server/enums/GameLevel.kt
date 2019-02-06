package com.kebuu.server.enums

import com.kebuu.core.action.FightAction
import com.kebuu.core.action.LimitedUseAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.board.BoardItem
import com.kebuu.core.board.Hole
import com.kebuu.core.board.Mountain
import com.kebuu.core.board.Treasure
import com.kebuu.core.gamer.Gamer
import com.kebuu.server.enums.BoardItemGenerator.holeGenerator
import com.kebuu.server.enums.BoardItemGenerator.mountainGenerator
import com.kebuu.server.enums.BoardItemGenerator.treasureGenerator
import com.kebuu.server.game.Game
import com.kebuu.server.gamer.bot.AggressiveBot
import com.kebuu.server.gamer.bot.DummyBot
import com.kebuu.server.gamer.bot.GreedyBot
import com.kebuu.server.gamer.bot.ImmobileBot
import java.util.*

enum class GameLevel(val enableSpawnUpdate: Boolean,
                     val forbiddenActions: List<Class<out StepAction>>,
                     val boardItemGenerators: Map<(game: Game) -> BoardItem, Int>,
                     val botGenerators: Map<(game: Game) -> Gamer, Int>,
                     val updateSpawnBasePoints: Int) {


    LEVEL_0(false,
            listOf(FightAction::class.java, LimitedUseAction::class.java),
            mapOf(treasureGenerator to 50),
            mapOf(BotGenerator.immobileGenerator to 2, BotGenerator.dummyGenerator to 2),
            0),
    LEVEL_1(true,
            listOf(FightAction::class.java, LimitedUseAction::class.java),
            mapOf(treasureGenerator to 20, holeGenerator to 10, mountainGenerator to 10),
            mapOf(BotGenerator.greedyGenerator to 4),
            2),
    LEVEL_2(true,
            listOf(),
            mapOf(treasureGenerator to 35, holeGenerator to 15, mountainGenerator to 15),
            mapOf(BotGenerator.dummyGenerator to 2, BotGenerator.aggressiveGenerator to 4, BotGenerator.greedyGenerator to 3),
            8);

    val random = Random(Date().time)

    val updateSpawnPoints: Int
        get() = updateSpawnBasePoints + random.nextInt(updateSpawnBasePoints / 2 + 1)
}

object BoardItemGenerator {

    private val random = Random()

    val treasureGenerator =  { game: Game ->
        Treasure(game.board.randomEmptyPosition(), (random.nextInt(9) + 1) * 10)
    }

    val holeGenerator =  { game: Game -> Hole(game.board.randomEmptyPosition())}
    val mountainGenerator =  { game: Game -> Mountain(game.board.randomEmptyPosition())}
}

object BotGenerator {

    val dummyGenerator =  { _: Game -> DummyBot() }
    val immobileGenerator =  { _: Game -> ImmobileBot() }
    val greedyGenerator =  { _: Game -> GreedyBot() }
    val aggressiveGenerator =  { _: Game -> AggressiveBot() }
}
