package com.kebuu.server.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
internal class HelloController {

    @RequestMapping("/hello")
    fun hello(): String  {
        return "Hello"
    }
}