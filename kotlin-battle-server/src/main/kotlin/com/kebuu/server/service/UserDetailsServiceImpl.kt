package com.kebuu.server.service

import com.kebuu.core.constant.FULL_ROLE_ADMIN
import com.kebuu.core.constant.FULL_ROLE_GAMER
import com.kebuu.server.bean.User
import com.kebuu.server.config.GameConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl @Autowired constructor(
        private val userRegistryService: UserRegistryService,
        private val gameConfig: GameConfig
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRegistryService.putIfAbsent(username) {
            val role = if (gameConfig.adminMail.isBlank() || username == gameConfig.adminMail) FULL_ROLE_ADMIN else FULL_ROLE_GAMER
            User(username, "/image/user.png", role)
        }
    }

    fun UserRegistryService.putIfAbsent(key: String, userSupplier: () -> User): UserDetails {
        var user: UserDetails? = getUser(key)

        user = user.let {
            val newUser = userSupplier()
            userRegistryService.putUser(newUser)
            newUser
        }

        return user
    }
}