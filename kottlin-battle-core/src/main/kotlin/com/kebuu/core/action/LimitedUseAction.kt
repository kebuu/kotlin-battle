package com.kebuu.core.action

interface LimitedUseAction : StepAction {

    fun getType(): LimitedUseActionType

    enum class LimitedUseActionType {
        HEAL, LIGHT_SPEED
    }
}