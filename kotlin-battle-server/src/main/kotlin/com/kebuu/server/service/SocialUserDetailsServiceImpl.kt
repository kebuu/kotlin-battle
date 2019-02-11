package com.kebuu.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.security.SocialUserDetails
import org.springframework.social.security.SocialUserDetailsService
import org.springframework.stereotype.Service

@Service
class SocialUserDetailsServiceImpl @Autowired constructor(
        private val userRegistryService: UserRegistryService
): SocialUserDetailsService {

    override fun loadUserByUserId(userId: String): SocialUserDetails? {
        return userRegistryService.getUser(userId)
    }
}