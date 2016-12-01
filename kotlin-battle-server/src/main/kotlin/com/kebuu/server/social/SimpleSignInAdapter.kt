package com.kebuu.server.social

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.social.connect.Connection
import org.springframework.social.connect.web.SignInAdapter
import org.springframework.social.security.SocialAuthenticationToken
import org.springframework.social.security.SocialUserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.context.request.NativeWebRequest

@Component
class SimpleSignInAdapter : SignInAdapter {

    @Autowired
    private val socialUserDetailsService: SocialUserDetailsService? = null

    override fun signIn(userId: String, connection: Connection<*>, request: NativeWebRequest): String {
        val socialUserDetails = socialUserDetailsService!!.loadUserByUserId(userId)

        val authentication = SocialAuthenticationToken(connection, socialUserDetails, null, socialUserDetails.authorities)
        SecurityContextHolder.getContext().authentication = authentication

        return "/"
    }

}