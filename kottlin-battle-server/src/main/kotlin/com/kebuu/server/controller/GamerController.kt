package com.kebuu.server.controller

import com.kebuu.core.gamer.Gamer
import com.kebuu.server.dto.GamerDto
import org.springframework.social.security.SocialAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class GamerController {

    @GetMapping("/users/logged")
    fun user(principal: Principal): GamerDto {
        return if (principal is SocialAuthenticationToken) {
            GamerDto(principal.principal as Gamer)
        } else throw IllegalStateException("Mais c'est quoi ce principal tout pourri $principal")
    }
}