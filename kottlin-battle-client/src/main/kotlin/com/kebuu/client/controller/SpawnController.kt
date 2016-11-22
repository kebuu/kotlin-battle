package com.kebuu.client.controller

import com.kebuu.client.service.SpawnService
import com.kebuu.core.board.spawn.SpawnAttributes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/spawn")
class SpawnController @Autowired constructor(val spawnService: SpawnService) {

    @GetMapping("/update")
    fun spawnUpdate(@RequestParam point: Int): SpawnAttributes {
        return spawnService.spawnUpdate(point)
    }
}
