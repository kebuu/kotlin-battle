package com.kebuu.core.enums

import com.kebuu.core.action.FightAction
import com.kebuu.core.action.LimitedUseAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.board.BoardItem
import com.kebuu.core.board.Hole
import com.kebuu.core.board.Mountain
import com.kebuu.core.board.Treasure
import com.kebuu.core.enums.BoardItemGenerator.holeGenerator
import com.kebuu.core.enums.BoardItemGenerator.mountainGenerator
import com.kebuu.core.enums.BoardItemGenerator.treasureGenerator
import com.kebuu.core.gamer.Gamer
import com.kebuu.server.game.Game
import com.kebuu.server.gamer.bot.DummyBot
import com.kebuu.server.gamer.bot.ImmobileBot
import java.util.*

enum class GameLevel(val enableSpawnUpdate: Boolean,
                     val forbiddenActions: List<Class<out StepAction>>,
                     val boardItemGenerators: Map<(game: Game) -> BoardItem, Int>,
                     val botGenerators: Map<(game: Game) -> Gamer, Int>) {

    LEVEL_0(false,
            listOf(FightAction::class.java, LimitedUseAction::class.java),
            mapOf(treasureGenerator to 50),
            mapOf(BotGenerator.dummyGenerator to 2)),
    LEVEL_1(false,
            listOf(FightAction::class.java),
            mapOf(treasureGenerator to 20, holeGenerator to 10, mountainGenerator to 10),
            mapOf(BotGenerator.dummyGenerator to 2)),
    LEVEL_2(true,
            listOf(),
            mapOf(treasureGenerator to 30, holeGenerator to 15, mountainGenerator to 15),
            mapOf(BotGenerator.immobileGenerator to 2, BotGenerator.dummyGenerator to 2))
}

object BoardItemGenerator {

    val random = Random()

    val treasureGenerator =  { game: Game ->
        Treasure(game.board.randomEmptyPosition(), (random.nextInt(5) + 1) * 100)
    }

    val holeGenerator =  { game: Game -> Hole(game.board.randomEmptyPosition())}
    val mountainGenerator =  { game: Game -> Mountain(game.board.randomEmptyPosition())}
}

object BotGenerator {

    val dummyGenerator =  { game: Game -> DummyBot() }
    val immobileGenerator =  { game: Game -> ImmobileBot() }
}
