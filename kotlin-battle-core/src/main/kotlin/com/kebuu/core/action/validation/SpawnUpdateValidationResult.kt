package com.kebuu.core.action.validation

class SpawnUpdateValidationResult(val message: String = String()) {

    companion object {
        fun ok() = SpawnUpdateValidationResult()
        fun withError(message: String) = SpawnUpdateValidationResult(message)
    }

    fun isOk(): Boolean = message.isBlank()
}