package com.kebuu.server.controller

import com.kebuu.server.dto.UserDto
import com.kebuu.server.utils.toUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class UserController {

    @GetMapping("/users/logged")
    fun user(principal: Principal): UserDto {
        val user = principal.toUser()
        return UserDto(user.username, user.email, user.authorities.toList())
    }
}