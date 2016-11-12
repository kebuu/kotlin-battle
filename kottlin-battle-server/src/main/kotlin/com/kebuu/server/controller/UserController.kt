package com.kebuu.server.controller

import com.kebuu.server.dto.UserDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class UserController {

    @GetMapping("/users/logged")
    fun user(principal: Principal): UserDto {
        return UserDto(principal.name)
    }
}