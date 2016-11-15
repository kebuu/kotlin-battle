package com.kebuu.server.social

import com.kebuu.core.constant.KotlinBattleConstant
import com.kebuu.server.bean.User
import com.kebuu.server.service.UserRegistryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionSignUp
import org.springframework.social.connect.UserProfile
import org.springframework.stereotype.Component

@Component
class AccountConnectionSignUp @Autowired constructor(val userRegistryService: UserRegistryService) : ConnectionSignUp {

    override fun execute(connection: Connection<*>): String {
        val profile = connection.fetchUserProfile()
        val role = if (profile.email == "christophe.tardella@zenika.com") KotlinBattleConstant.FULL_ROLE_ADMIN else KotlinBattleConstant.FULL_ROLE_GAMER
        val user = User(username(profile), connection.imageUrl, profile.email, role)
        userRegistryService.putUser(user)
        return user.email
    }

    private fun  username(profile: UserProfile) = profile.email.substringBefore("@")

}