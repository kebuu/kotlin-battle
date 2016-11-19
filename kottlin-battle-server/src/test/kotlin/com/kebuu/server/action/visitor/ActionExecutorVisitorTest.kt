package com.kebuu.server.action.visitor

import com.kebuu.core.Position
import com.kebuu.core.action.*
import com.kebuu.core.board.Board
import com.kebuu.core.board.Treasure
import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.gamer.Gamer
import com.kebuu.server.config.GameConfig
import com.kebuu.server.game.Game
import com.kebuu.server.gamer.bot.DummyBot
import com.kebuu.server.service.EventLogService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class ActionExecutorVisitorTest {

    lateinit var executor: ActionExecutorVisitor
    lateinit var board: Board
    lateinit var game: Game
    lateinit var gameConfig: GameConfig
    lateinit var gamer1: Gamer
    lateinit var gamer1Spawn: Spawn
    lateinit var gamer2: Gamer
    lateinit var gamer2Spawn: Spawn

    @Before
    fun setUp() {
        gameConfig = GameConfig()
        game = Game(gameConfig, Mockito.mock(EventLogService::class.java))
        board = game.board
        gamer1 = DummyBot()
        gamer1Spawn = Spawn(gamer1, SpawnAttributes(3, 4, 2, 2))
        gamer2 = DummyBot()
        gamer2Spawn = Spawn(gamer2, SpawnAttributes(5, 1, 2, 1))
        executor = ActionExecutorVisitor(game, gamer1)

        game.addGamers(gamer1, gamer2)
        board.addItem(gamer1Spawn)
        board.addItem(gamer2Spawn)
    }

    @Test
    fun executeNoAction() {
        assertThat(executor.execute(NoAction())).isNotEmpty()
    }

    @Test
    fun executeMoveAction() {
        val moveToPosition = Position(5, 5)
        executor.execute(MoveAction(moveToPosition))

        assertThat(gamer1Spawn.position).isEqualTo(moveToPosition)
    }

    @Test
    fun executeDigAction() {
        val initialZPoints = gamer1.getZPoints()

        val treasureZPoints = 100
        board.addItem(Treasure(Position.ORIGIN, treasureZPoints))

        executor.execute(DigAction())

        assertThat(gamer1.getZPoints()).isEqualTo(initialZPoints + treasureZPoints)
    }

    @Test
    fun executeFightAction() {
        gamer2.setLife(4)

        executor.execute(FightAction(gamer2.pseudo()))
        assertThat(gamer2.getLife()).isEqualTo(2)
        assertThat(gamer1.getZPoints()).isEqualTo(100)

        executor.execute(FightAction(gamer2.pseudo()))
        assertThat(gamer2.getLife()).isEqualTo(0)
        assertThat(gamer1.getZPoints()).isEqualTo(115)

        gamer2.setLife(4)
        gamer2Spawn.attributes.resistance = 10
        executor.execute(FightAction(gamer2.pseudo()))
        assertThat(gamer2.getLife()).isEqualTo(3)
        assertThat(gamer1.getZPoints()).isEqualTo(115)
    }

    @Test
    fun executeLightSpeedMoveAction() {
        val moveToPosition = Position(5, 5)
        val lightSpeedMoveAction = LightSpeedMoveAction(MoveAction(moveToPosition))
        val usedAction = game.getLimitedActionUsedBy(gamer1, lightSpeedMoveAction.getType())

        executor.execute(lightSpeedMoveAction)

        assertThat(gamer1Spawn.position).isEqualTo(moveToPosition)
        assertThat(game.getLimitedActionUsedBy(gamer1, lightSpeedMoveAction.getType())).isEqualTo(usedAction + 1)
    }

    @Test
    fun executeHealAction() {
        gamer1.setLife(0)
        val HealAction = HealAction()
        val usedAction = game.getLimitedActionUsedBy(gamer1, HealAction.getType())

        executor.execute(HealAction)

        assertThat(game.getLimitedActionUsedBy(gamer1, HealAction.getType())).isEqualTo(usedAction + 1)
        assertThat(gamer1.getLife()).isEqualTo(gamer1Spawn.attributes.resistance)
    }

    @Test
    fun executeExceptionAction() {
        assertThat(executor.execute(NoAction())).isNotEmpty()
    }

    @Test
    fun executeTimeoutAction() {
        assertThat(executor.execute(NoAction())).isNotEmpty()
    }

}
