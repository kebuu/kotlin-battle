package com.kebuu.server.service

import com.kebuu.server.bean.GameEvent
import com.kebuu.server.dto.GameDto
import com.kebuu.server.game.Game
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class WebSocketService @Autowired constructor(val webSocketTemplate: SimpMessagingTemplate) {

    fun sendGame(game: Game) {
        webSocketTemplate.convertAndSend("/topic/active-game", GameDto(game))
    }

    fun sendEvent(gameEvent: GameEvent) {
        webSocketTemplate.convertAndSend("/topic/event", gameEvent)
    }
}