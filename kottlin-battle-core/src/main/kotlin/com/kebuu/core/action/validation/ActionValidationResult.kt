package com.kebuu.core.action.validation

class ActionValidationResult private constructor() {

    private val validationErrorMessages = mutableListOf<String>()

    companion object {
        fun ok() = ActionValidationResult()
        fun withError(message: String) = ActionValidationResult().addErrorMessage(message)
        fun resultFrom(result: ActionValidationResult) = ActionValidationResult().addErrorMessages(result)
    }

    fun isOk(): Boolean = validationErrorMessages.isEmpty()

    fun getValidationErrorMessages() = validationErrorMessages.toList()

    fun addErrorMessage(message:String): ActionValidationResult {
        validationErrorMessages.add(message)
        return this
    }
    fun addErrorMessages(actionValidationResult: ActionValidationResult): ActionValidationResult {
        validationErrorMessages.addAll(actionValidationResult.validationErrorMessages)
        return this
    }
}