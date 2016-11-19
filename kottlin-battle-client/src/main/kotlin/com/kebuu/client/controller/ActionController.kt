package com.kebuu.client.controller

import com.kebuu.core.Dimension
import com.kebuu.core.Position
import com.kebuu.core.action.MoveAction
import com.kebuu.core.action.NoAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.dto.GameInfo
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/actions/next")
class ActionController {

    companion object {
        val operations = listOf(
                { position: Position -> position.plusX(1)},
                { position: Position -> position.plusY(1)},
                { position: Position -> position.plusX(-1)},
                { position: Position -> position.plusY(-1)}
        )

        lateinit var boardDimension: Dimension
    }

    @PutMapping
    fun action(@RequestBody gameInfo: GameInfo): StepAction {
        if(gameInfo.board.dimension != null) {
            boardDimension = gameInfo.board.dimension!!
        }

        return operations.map { it(gameInfo.position) }
                .map(::MoveAction)
                .firstOrNull { it.goTo.x in 0..(boardDimension.x - 1)  &&
                        it.goTo.y in 0..(boardDimension.y - 1) }
                ?: NoAction()
    }
}