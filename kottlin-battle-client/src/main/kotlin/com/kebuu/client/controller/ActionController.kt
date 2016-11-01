package com.kebuu.client.controller

import com.kebuu.core.Position
import com.kebuu.core.action.MoveAction
import com.kebuu.core.action.NoAction
import com.kebuu.core.action.StepAction
import com.kebuu.core.dto.GameInfo
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalTime

@RestController
@RequestMapping("/actions/next")
internal class ActionController {

    @PutMapping
    fun action(@RequestBody gameInfo: GameInfo): StepAction {
        var action: StepAction = NoAction()

        if(LocalTime.now().second % 2 == 0) {
            action = MoveAction(Position(10, 10))
        }

        return action
    }
}