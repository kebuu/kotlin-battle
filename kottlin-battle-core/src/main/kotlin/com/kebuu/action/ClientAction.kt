package com.kebuu.action

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes(
        JsonSubTypes.Type(value = NoAction::class, name = "none"),
        JsonSubTypes.Type(value = MoveAction::class, name = "move")
)
interface ClientAction {

}