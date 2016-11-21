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

class ActionValidatorVisitorTest {

    lateinit var validator: ActionValidatorVisitor
    lateinit var board: Board
    lateinit var game: Game
    lateinit var gameConfig: GameConfig
    lateinit var gamer1: Gamer
    lateinit var gamer2: Gamer
    lateinit var gamer2Spawn: Spawn

    @Before
    fun setUp() {
        gameConfig = GameConfig()
        game = Game(gameConfig, Mockito.mock(EventLogService::class.java))
        board = game.board
        gamer1 = DummyBot()
        gamer2 = DummyBot()
        gamer2Spawn = Spawn(gamer2, SpawnAttributes(5, 1, 2, 1))
        validator = ActionValidatorVisitor(game, gamer1)

        game.addGamers(gamer1, gamer2)
        board.addItem(Spawn(gamer1, SpawnAttributes(3, 4, 2, 2)))
        board.addItem(gamer2Spawn)
    }

    @Test
    fun validateNoAction() {
        assertThat(validator.validate(NoAction()).isOk()).isTrue()
    }

    @Test
    fun validateMoveAction() {
        assertThat(validator.validate(MoveAction(Position(0, 1))).isOk()).isTrue()
        assertThat(validator.validate(MoveAction(Position(2, 1))).isOk()).isTrue()
        assertThat(validator.validate(MoveAction(Position(2, 2))).isOk()).isFalse()
    }

    @Test
    fun validateDigAction() {
        assertThat(validator.validate(DigAction()).isOk()).isFalse()

        val treasure = Treasure(Position.ORIGIN)
        board.addItem(treasure)
        assertThat(validator.validate(DigAction()).isOk()).isTrue()

        treasure.discoveredAtStep = 0
        assertThat(validator.validate(DigAction()).isOk()).isTrue()

        game.currentStep = 1
        assertThat(validator.validate(DigAction()).isOk()).isFalse()
    }

    @Test
    fun validateFightAction() {
        assertThat(validator.validate(FightAction("unknownGamer")).isOk()).isFalse()
        assertThat(validator.validate(FightAction(gamer1.gamerId())).isOk()).isFalse()
        assertThat(validator.validate(FightAction(gamer2.gamerId())).isOk()).isTrue()

        gamer2Spawn.position = Position(1, 1)
        assertThat(validator.validate(FightAction(gamer2.gamerId())).isOk()).isTrue()

        gamer2Spawn.position = Position(2, 1)
        assertThat(validator.validate(FightAction(gamer2.gamerId())).isOk()).isFalse()
    }

    @Test
    fun validateLightSpeedMoveAction() {
        val lightSpeedMoveAction = LightSpeedMoveAction(MoveAction(Position(3, 3)))
        assertThat(validator.validate(lightSpeedMoveAction).isOk()).isTrue()

        gameConfig.limitedActionAllowedOccurrence.put(LimitedUseAction.LimitedUseActionType.LIGHT_SPEED, 0)
        assertThat(validator.validate(lightSpeedMoveAction).isOk()).isFalse()

        gameConfig.limitedActionAllowedOccurrence.put(LimitedUseAction.LimitedUseActionType.LIGHT_SPEED, 1)
        assertThat(validator.validate(lightSpeedMoveAction).isOk()).isTrue()

        game.gamerUsedLimitedAction(gamer1, lightSpeedMoveAction)
        assertThat(validator.validate(lightSpeedMoveAction).isOk()).isFalse()
    }

    @Test
    fun validateHealAction() {
        val healAction = HealAction()
        assertThat(validator.validate(healAction).isOk()).isTrue()

        gameConfig.limitedActionAllowedOccurrence.put(LimitedUseAction.LimitedUseActionType.HEAL, 0)
        assertThat(validator.validate(healAction).isOk()).isFalse()

        gameConfig.limitedActionAllowedOccurrence.put(LimitedUseAction.LimitedUseActionType.HEAL, 1)
        assertThat(validator.validate(healAction).isOk()).isTrue()

        game.gamerUsedLimitedAction(gamer1, healAction)
        assertThat(validator.validate(healAction).isOk()).isFalse()
    }
}