package com.kebuu.client.controller

import com.kebuu.client.service.ActionService
import com.kebuu.core.action.StepAction
import com.kebuu.core.dto.GameInfo
import com.kebuu.core.utils.Loggable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/actions/next")
class ActionController @Autowired constructor(val actionService: ActionService) : Loggable {

    @PutMapping
    fun action(@RequestBody gameInfo: GameInfo, bindingResult: BindingResult): StepAction {
        logger.info("Recuperation action")

        if (bindingResult.hasErrors()) {
            logger.error(bindingResult.allErrors.toString())
        }

        val action = actionService.action(gameInfo)
        logger.info(action.toString())
        return action
    }
}