package com.kebuu.core.action

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.kebuu.core.action.execution.ActionExecutor
import com.kebuu.core.action.validation.ActionValidationResult
import com.kebuu.core.action.validation.ActionValidator

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes(
        JsonSubTypes.Type(value = NoAction::class, name = "none"),
        JsonSubTypes.Type(value = MoveAction::class, name = "move"),
        JsonSubTypes.Type(value = DigAction::class, name = "dig"),
        JsonSubTypes.Type(value = FightAction::class, name = "fight"),
        JsonSubTypes.Type(value = HealthAction::class, name = "health"),
        JsonSubTypes.Type(value = LightSpeedMoveAction::class, name = "light")
)
interface StepAction {

    fun validateBy(validator: ActionValidator): ActionValidationResult
    fun executeBy(executor: ActionExecutor): String
}

