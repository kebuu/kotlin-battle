package com.kebuu.core.action

class StepActionValidationResult {

    private val validationErrorMessages = mutableListOf<String>()

    companion object {
        fun ok() = StepActionValidationResult()
    }

    fun isActionValid(): Boolean = validationErrorMessages.isEmpty()

    fun getValidationErrorMessages() = validationErrorMessages.toList()

    fun addErrorMessage(message:String) {
        validationErrorMessages.add(message)
    }
    fun addErrorMessages(stepActionValidationResult: StepActionValidationResult) {
        validationErrorMessages.addAll(stepActionValidationResult.validationErrorMessages)
    }
}