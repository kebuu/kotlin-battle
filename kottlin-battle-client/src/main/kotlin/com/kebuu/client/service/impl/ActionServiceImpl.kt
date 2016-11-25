package com.kebuu.client.service.impl

import com.kebuu.client.service.ActionService
import com.kebuu.core.action.NoAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.dto.GameInfo
import org.springframework.stereotype.Service

@Service
class ActionServiceImpl : ActionService {

    override fun action(gameInfo: GameInfo): StepAction {
        return NoAction()
    }
}