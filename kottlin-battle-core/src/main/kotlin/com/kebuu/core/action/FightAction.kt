package com.kebuu.core.action

import com.kebuu.core.action.execution.ActionExecutor
import com.kebuu.core.action.validation.ActionValidator

data class FightAction(val attackedGamerPseudo: String): StepAction {

    override fun validateBy(validator: ActionValidator) = validator.validate(this)

    override fun executeBy(executor: ActionExecutor) = executor.execute(this)
}