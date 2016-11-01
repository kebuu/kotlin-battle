package com.kebuu.core.action

class ExceptionAction : StepAction {

    override fun isValid(validator: StepActionValidator): StepActionValidationResult {
        throw UnsupportedOperationException()
    }

}