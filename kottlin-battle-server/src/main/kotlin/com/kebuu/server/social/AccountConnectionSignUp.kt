package com.kebuu.server.social

import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionSignUp

class AccountConnectionSignUp : ConnectionSignUp {

    override fun execute(connection: Connection<*>): String {
        val profile = connection.fetchUserProfile()
        val mailDomain = profile.email.substring(profile.email.indexOf("@") + 1)
        val id = mailDomain
        return id
    }

}