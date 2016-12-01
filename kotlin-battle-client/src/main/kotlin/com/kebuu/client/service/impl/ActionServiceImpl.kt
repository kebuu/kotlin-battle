package com.kebuu.client.service.impl

import com.kebuu.client.service.ActionService
import com.kebuu.core.Dimension
import com.kebuu.core.Position
import com.kebuu.core.action.NoAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.dto.GameInfo
import org.springframework.stereotype.Service

@Service
class ActionServiceImpl : ActionService {

    companion object {
        val operations = listOf(
                { position: Position -> position.plusX(1)},
                { position: Position -> position.plusY(1)},
                { position: Position -> position.plusX(-1)},
                { position: Position -> position.plusY(-1)}
        )

        lateinit var boardDimension: Dimension
    }

    override fun action(gameInfo: GameInfo): StepAction {
        return NoAction()
    }
}