package com.kebuu.core.dto

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.kebuu.core.Position
import com.kebuu.core.board.Board
import com.kebuu.core.board.Hole
import com.kebuu.core.board.Mountain
import com.kebuu.core.board.Treasure
import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.dto.board.item.SpawnBoardItemDto
import com.kebuu.core.dto.board.item.TreasureBoardItemDto
import com.kebuu.core.gamer.RemoteGamer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.springframework.web.client.RestTemplate

class GameInfoTest {

    lateinit var mapper: ObjectMapper
    lateinit var board: Board
    val spawn = Spawn(RemoteGamer("test", "host", 0, restOperations = RestTemplate()))

    @Before
    fun setUp() {
        board = Board()
        board.addItem(Hole(Position.ORIGIN))
        board.addItem(Mountain(Position.ORIGIN))
        board.addItem(Treasure(Position.ORIGIN))
        board.addItem(spawn)

        mapper = ObjectMapper()
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    @Test
    fun testSerializationDeserialization_step1() {
        val currentStep = 1
        val endStepNumber = 10
        val gameInfo = GameInfo(spawn, board, currentStep, endStepNumber)

        val gameInfo2 = mapper.readValue(mapper.writeValueAsString(gameInfo), GameInfo::class.java)

        assertThat(gameInfo2.board.dimension).isNotNull()
        assertThat(gameInfo2.lastStepNumber).isEqualTo(endStepNumber)
        assertThat(gameInfo2.board.boardItems).hasSize(4)
    }

    @Test
    fun testSerializationDeserialization_step2() {
        val currentStep = 2
        val endStepNumber = 10
        val gameInfo = GameInfo(spawn, board, currentStep, endStepNumber)

        val gameInfo2 = mapper.readValue(mapper.writeValueAsString(gameInfo), GameInfo::class.java)

        assertThat(gameInfo2.board.dimension).isNull()
        assertThat(gameInfo2.lastStepNumber).isNull()
        assertThat(gameInfo2.board.boardItems).hasSize(2)
        assertThat(gameInfo2.board.boardItems.all { it is TreasureBoardItemDto || it is SpawnBoardItemDto }).isTrue()
    }
}