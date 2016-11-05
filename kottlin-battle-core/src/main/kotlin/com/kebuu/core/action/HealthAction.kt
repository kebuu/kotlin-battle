package com.kebuu.core.action

import com.kebuu.core.action.execution.ActionExecutor
import com.kebuu.core.action.validation.ActionValidator

class HealthAction: StepAction, LimitedUseAction {

    override fun getType(): LimitedUseAction.LimitedUseActionType {
        return LimitedUseAction.LimitedUseActionType.HEALTH
    }

    override fun validateBy(validator: ActionValidator) = validator.validate(this)

    override fun executeBy(executor: ActionExecutor) = executor.execute(this)
}

