package com.kebuu.client.controller

import com.kebuu.client.service.ActionService
import com.kebuu.core.action.StepAction
import com.kebuu.core.dto.GameInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/actions/next")
class ActionController @Autowired constructor(val actionService: ActionService) {

    @PutMapping
    fun action(@RequestBody gameInfo: GameInfo): StepAction {
        return actionService.action(gameInfo)
    }
}