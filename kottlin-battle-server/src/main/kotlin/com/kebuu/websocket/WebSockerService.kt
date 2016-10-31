package com.kebuu.websocket

import com.kebuu.dto.GameDto
import com.kebuu.game.Game
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
open class WebSockerService @Autowired constructor(val webSocketTemplate: SimpMessagingTemplate) {

    fun sendGame(game: Game) {
        webSocketTemplate.convertAndSend("/topic/active-game", GameDto(game))
    }
}