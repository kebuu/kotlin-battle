package com.kebuu.core.action

interface LimitedUseAction {

    fun getType(): LimitedUseActionType

    enum class LimitedUseActionType {
        HEALTH, LIGHT_SPEED
    }
}