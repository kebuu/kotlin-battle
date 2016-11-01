package com.kebuu.core.action

import com.kebuu.core.Position

open class MoveAction(var goTo: Position = Position.ORIGIN): StepAction {

    override fun isValid(validator: StepActionValidator) = validator.validate(this)

}