package com.kebuu.server.bean

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
        JsonSubTypes.Type(value = GameEvent::class, name = "info"),
        JsonSubTypes.Type(value = WarnGameEvent::class, name = "warn"),
        JsonSubTypes.Type(value = ErrorGameEvent::class, name = "error")
)
open class GameEvent(val username: String, val gameStep: Int, val message: String) {

    val timestamp: Long = System.nanoTime()

}

class WarnGameEvent(username: String, gameStep: Int, message: String) : GameEvent(username, gameStep, message)
class ErrorGameEvent(username: String, gameStep: Int, message: String) : GameEvent(username, gameStep, message)