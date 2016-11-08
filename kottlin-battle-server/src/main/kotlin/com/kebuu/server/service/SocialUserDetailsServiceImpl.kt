package com.kebuu.server.service

import com.kebuu.server.gamer.bot.RemoteGamer
import org.springframework.social.security.SocialUserDetails
import org.springframework.social.security.SocialUserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class SocialUserDetailsServiceImpl : SocialUserDetailsService {

    override fun loadUserByUserId(userId: String): SocialUserDetails {
        return RemoteGamer(userId, "", 0, null, RestTemplate())
    }
}