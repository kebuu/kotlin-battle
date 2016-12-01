package com.kebuu.server.social

import com.kebuu.core.constant.KotlinBattleConstant
import com.kebuu.server.bean.User
import com.kebuu.server.config.GameConfig
import com.kebuu.server.service.UserRegistryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionSignUp
import org.springframework.stereotype.Component

@Component
class AccountConnectionSignUp @Autowired constructor(val userRegistryService: UserRegistryService, val gameConfig: GameConfig) : ConnectionSignUp {

    override fun execute(connection: Connection<*>): String {
        val profile = connection.fetchUserProfile()
        val role = if (gameConfig.adminMail.isNullOrBlank() || profile.email == gameConfig.adminMail) KotlinBattleConstant.FULL_ROLE_ADMIN else KotlinBattleConstant.FULL_ROLE_GAMER
        val user = User(profile.email, connection.imageUrl, role)
        userRegistryService.putUser(user)
        return user.email
    }
}