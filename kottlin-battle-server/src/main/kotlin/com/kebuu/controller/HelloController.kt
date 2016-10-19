package com.kebuu.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
internal class HelloController {

    @RequestMapping("/")
    fun hello(): String  {
        return "Hello"
    }
}