package com.kebuu.server.service

import com.kebuu.core.utils.Loggable
import com.kebuu.server.bean.GameEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventLogService @Autowired constructor(private val webSocketService: WebSocketService) : Loggable {

    fun logEvent(gameEvent: GameEvent) {
        webSocketService.sendEvent(gameEvent)
        logger.info(gameEvent.message)
    }
}