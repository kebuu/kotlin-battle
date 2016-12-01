package com.kebuu.core.action.validation

class SpawnUpdateValidationResult {

    val message: String

    private constructor() {
        message = String()
    }

    private constructor(message: String) {
        this.message = message
    }

    companion object {
        fun ok() = SpawnUpdateValidationResult()
        fun withError(message: String) = SpawnUpdateValidationResult(message)
    }

    fun isOk(): Boolean = message.isNullOrBlank()
}