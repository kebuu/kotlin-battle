package com.kebuu.core.action.validation

import com.kebuu.core.action.*

interface ActionValidator {

    fun validate(noAction: NoAction) = ActionValidationResult.ok()
    fun validate(digAction: DigAction): ActionValidationResult
    fun validate(moveAction: MoveAction): ActionValidationResult
    fun validate(lightSpeedMoveAction: LightSpeedMoveAction): ActionValidationResult
    fun validate(fightAction: FightAction): ActionValidationResult
    fun validate(healAction: HealAction): ActionValidationResult

}