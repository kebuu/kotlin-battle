package com.kebuu.controller

import com.kebuu.Board
import com.kebuu.action.ClientAction
import com.kebuu.action.NoAction
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/game")
internal class GameController {

    @PostMapping("/init")
    fun action(@RequestBody board: Board): ClientAction {
        println(board)

        return NoAction()
    }
}