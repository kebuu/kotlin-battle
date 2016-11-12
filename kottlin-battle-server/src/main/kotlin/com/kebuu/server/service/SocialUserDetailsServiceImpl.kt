package com.kebuu.server.service

import org.springframework.social.security.SocialUserDetails
import org.springframework.social.security.SocialUserDetailsService
import org.springframework.stereotype.Service

@Service
class SocialUserDetailsServiceImpl(val userRegistryService: UserRegistryService): SocialUserDetailsService {

    override fun loadUserByUserId(userId: String): SocialUserDetails? {
        return userRegistryService.getUser(userId)
    }
}