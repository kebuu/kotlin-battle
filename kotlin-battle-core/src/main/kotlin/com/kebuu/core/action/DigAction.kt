package com.kebuu.core.action

import com.kebuu.core.action.execution.ActionExecutor
import com.kebuu.core.action.validation.ActionValidator
import com.kebuu.core.utils.equalsOnType

class DigAction: StepAction {

    override fun validateBy(validator: ActionValidator) = validator.validate(this)

    override fun executeBy(executor: ActionExecutor) = executor.execute(this)

    override fun equals(other: Any?): Boolean = equalsOnType(this, other)

    override fun hashCode(): Int = javaClass.hashCode()
}