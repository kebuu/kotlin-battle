package com.kebuu.core.action.error

import com.kebuu.core.action.StepAction
import com.kebuu.core.action.execution.ActionExecutor
import com.kebuu.core.action.validation.ActionValidationResult
import com.kebuu.core.action.validation.ActionValidator

class TimeoutAction : StepAction {

    override fun executeBy(executor: ActionExecutor) = executor.execute(this)

    override fun validateBy(validator: ActionValidator) = ActionValidationResult.ok()

}