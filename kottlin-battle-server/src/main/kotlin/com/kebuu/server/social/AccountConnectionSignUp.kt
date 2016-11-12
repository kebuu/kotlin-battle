package com.kebuu.server.social

import com.kebuu.server.bean.User
import com.kebuu.server.service.UserRegistryService
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionSignUp
import org.springframework.stereotype.Component

@Component
class AccountConnectionSignUp(val userRegistryService: UserRegistryService) : ConnectionSignUp {

    override fun execute(connection: Connection<*>): String {
        val profile = connection.fetchUserProfile()
        val user = User(profile.username, connection.imageUrl, profile.email)
        userRegistryService.putUser(user)
        return user.username
    }

}