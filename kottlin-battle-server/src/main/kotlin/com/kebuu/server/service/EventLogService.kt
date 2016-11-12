package com.kebuu.server.service

import com.kebuu.server.bean.GameEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class EventLogService @Autowired constructor(private val webSocketService: WebSocketService) {

    fun logEvent(gameEvent: GameEvent) {
        webSocketService.sendEvent(gameEvent)
    }
}