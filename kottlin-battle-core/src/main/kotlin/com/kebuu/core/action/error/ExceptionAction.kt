package com.kebuu.core.action.error

import com.kebuu.core.action.StepAction
import com.kebuu.core.action.execution.ActionExecutor
import com.kebuu.core.action.validation.ActionValidationResult
import com.kebuu.core.action.validation.ActionValidator

class ExceptionAction : StepAction {

    // Todo mieux traiter ce cas
    override fun executeBy(executor: ActionExecutor) = String()

    override fun validateBy(validator: ActionValidator) = ActionValidationResult.ok()

}