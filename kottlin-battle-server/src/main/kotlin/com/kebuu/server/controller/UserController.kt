package com.kebuu.server.controller

import com.kebuu.server.dto.UserDto
import com.kebuu.server.service.UserRegistryService
import com.kebuu.server.utils.toUser
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class UserController(val userRegistryService: UserRegistryService) {

    @GetMapping("/users/logged")
    fun user(principal: Principal): UserDto {
        val user = principal.toUser()
        return UserDto(user.username, user.email, user.authorities.toList())
    }

    @Profile("!no-internet")
    @GetMapping("/user/config")
    fun avatarUrl(@RequestParam avatarUrl: String, principal: Principal): ResponseEntity<String> {
        val user = principal.toUser()
        user.avatarUrl = avatarUrl
        userRegistryService.putUser(user)
        return ResponseEntity.ok("OK")
    }
}