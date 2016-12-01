package com.kebuu.server.game

import com.kebuu.core.Position
import com.kebuu.core.board.Hole
import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.gamer.Gamer
import com.kebuu.server.config.GameConfig
import com.kebuu.server.gamer.bot.ImmobileBot
import com.kebuu.server.service.EventLogService
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GameTest {

    lateinit var game: Game
    lateinit var gamer1: Gamer
    lateinit var gamer1Spawn: Spawn
    lateinit var gameConfig: GameConfig

    @Before
    fun setUp() {
        gameConfig = GameConfig()
        game = Game(gameConfig, Mockito.mock(EventLogService::class.java))
        gamer1 = ImmobileBot()
        gamer1.setLife(gameConfig.gamerLife)
        gamer1Spawn = Spawn(gamer1, SpawnAttributes(5, 1, 2, 1))

        game.addGamers(gamer1)
        game.board.addItem(gamer1Spawn)
    }

    @Test
    fun runStep_GamerOnHoleShouldBeMoved() {
        gamer1.setLife(10)
        game.board.addItem(Hole(Position.ORIGIN))

        game.runStep(0)

        Assertions.assertThat(gamer1Spawn.position).isNotEqualTo(Position.ORIGIN)
        Assertions.assertThat(gamer1.getLife()).isEqualTo(10)
        Assertions.assertThat(gamer1.getZPoints()).isEqualTo(80)
    }


    @Test
    fun runStep_DeadGamerShouldBeMovedAndLoseZPoints() {
        gamer1.setLife(0)

        game.runStep(0)

        Assertions.assertThat(gamer1Spawn.position).isNotEqualTo(Position.ORIGIN)
        Assertions.assertThat(gamer1.getLife()).isEqualTo(gameConfig.gamerLife)
        Assertions.assertThat(gamer1.getZPoints()).isEqualTo(85)
    }
}