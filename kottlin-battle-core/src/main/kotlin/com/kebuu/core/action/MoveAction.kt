package com.kebuu.core.action

import com.kebuu.core.Position
import com.kebuu.core.action.execution.ActionExecutor
import com.kebuu.core.action.validation.ActionValidator

class MoveAction(var goTo: Position): StepAction {

    override fun validateBy(validator: ActionValidator) = validator.validate(this)

    override fun executeBy(executor: ActionExecutor) = executor.execute(this)
}