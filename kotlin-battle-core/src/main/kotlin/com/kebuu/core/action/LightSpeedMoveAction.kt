package com.kebuu.core.action

import com.kebuu.core.action.execution.ActionExecutor
import com.kebuu.core.action.validation.ActionValidator

data class LightSpeedMoveAction(val moveAction: MoveAction): LimitedUseAction {

    override fun getType(): LimitedUseAction.LimitedUseActionType = LimitedUseAction.LimitedUseActionType.LIGHT_SPEED

    override fun validateBy(validator: ActionValidator) = validator.validate(this)

    override fun executeBy(executor: ActionExecutor) = executor.execute(this)
}