package com.kebuu.client.controller

import com.kebuu.client.service.SpawnService
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.utils.Loggable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/spawn")
class SpawnController @Autowired constructor(private val spawnService: SpawnService) : Loggable {

    @GetMapping("/update")
    fun spawnUpdate(@RequestParam point: Int): SpawnAttributes {
        logger.info("spawnUpdate : $point")
        return spawnService.spawnUpdate(point)
    }
}