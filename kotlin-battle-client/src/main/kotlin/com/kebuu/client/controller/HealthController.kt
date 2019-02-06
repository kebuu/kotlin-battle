package com.kebuu.client.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
internal class HealthController {

    @GetMapping("/")
    fun ok(): String = "ok"
}