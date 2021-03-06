package com.kebuu.server.bean

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.social.security.SocialUserDetails

class User(val email: String, var avatarUrl: String?, val role: String) : SocialUserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role))
    }

    override fun getUsername() = email
    override fun isCredentialsNonExpired() = true
    override fun getUserId() = email
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isEnabled() = true
    override fun getPassword() = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int = email.hashCode()
}