package com.kebuu.client.controller

import com.kebuu.core.board.spawn.SpawnAttributes
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/spawn")
internal class SpawnController {

    @PutMapping("/update")
    fun action(@RequestParam point: Int): SpawnAttributes {
        return SpawnAttributes()
    }
}