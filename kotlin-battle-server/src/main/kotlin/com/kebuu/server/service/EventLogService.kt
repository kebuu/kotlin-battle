package com.kebuu.server.service

import com.kebuu.server.bean.GameEvent

interface EventLogService {

    fun logEvent(gameEvent: GameEvent)
}