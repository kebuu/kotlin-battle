package com.kebuu.core.action

open class NoAction: StepAction {

    override fun isValid(validator: StepActionValidator) = validator.validate(this)


}