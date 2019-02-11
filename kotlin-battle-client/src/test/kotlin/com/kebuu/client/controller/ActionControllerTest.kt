package com.kebuu.client.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.kebuu.client.KotlinBattleClientApplication
import com.kebuu.client.service.ActionService
import com.kebuu.core.Position
import com.kebuu.core.action.*
import com.kebuu.core.board.Board
import com.kebuu.core.board.Hole
import com.kebuu.core.board.Mountain
import com.kebuu.core.board.Treasure
import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.dto.GameInfo
import com.kebuu.core.gamer.RemoteGamer
import com.kebuu.test.any
import com.kebuu.test.whenMock
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = arrayOf(KotlinBattleClientApplication::class, ActionControllerTestConfiguration::class))
class ActionControllerTest {

    @Autowired lateinit var webApplicationContext: WebApplicationContext
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var actionService: ActionService

    private lateinit var mvc: MockMvc

    @Before
    fun setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun testGetNextAction() {
        simpleTest(MoveAction(Position(1, 1)))
        simpleTest(DigAction())
        simpleTest(HealAction())
        simpleTest(FightAction("gamerId"))
        simpleTest(LightSpeedMoveAction(MoveAction(Position(1, 1))))
        simpleTest(NoAction())
    }

    private fun simpleTest(action: StepAction, gameStep: Int = 1) {
        Mockito.reset(actionService)
        whenMock(actionService.action(any())).thenReturn(action)

        val gameInfo = gameInfo(gameStep)

        val result = mvc.perform(MockMvcRequestBuilders.put("/actions/next").accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameInfo))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        val actionResult = objectMapper.readValue(result.response.contentAsString, StepAction::class.java)
        Assertions.assertThat(actionResult).isEqualTo(action)
    }

    private fun gameInfo(gameStep: Int): GameInfo {
        val spawn = Spawn(RemoteGamer("test", "host", 0, restOperations = RestTemplate()))

        val board = Board()
        board.addItem(Hole(Position.ORIGIN))
        board.addItem(Mountain(Position(0, gameStep)))
        board.addItem(Treasure(Position(gameStep, gameStep)))
        board.addItem(spawn)

        val gameInfo = GameInfo(spawn, board, gameStep)
        return gameInfo
    }
}

@Configuration
open private class ActionControllerTestConfiguration {

    @Bean
    @Primary
    open fun actionService(): ActionService {
        return Mockito.mock(ActionService::class.java)
    }
}