package com.kebuu.core.action

interface StepActionValidator {

    fun validate(noAction: NoAction) = StepActionValidationResult.ok()

    fun validate(moveAction: MoveAction): StepActionValidationResult

}