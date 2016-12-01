package com.kebuu.client.service

import com.kebuu.core.action.StepAction
import com.kebuu.core.dto.GameInfo


interface ActionService {

    fun action(gameInfo: GameInfo): StepAction
}