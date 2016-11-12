package com.kebuu.server.bean

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.social.security.SocialUserDetails

class User(val pseudo: String, val avatarUrl: String?, val email: String) : SocialUserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()
        if (pseudo == "christophe.tardella") {
            authorities.add(SimpleGrantedAuthority("ROLE_ADMIN"))
        }
        return authorities
    }

    override fun getUsername() = pseudo
    override fun isCredentialsNonExpired() = false
    override fun getUserId() = username
    override fun isAccountNonExpired() = false
    override fun isAccountNonLocked() = true
    override fun isEnabled() = true
    override fun getPassword() = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (pseudo != other.pseudo) return false

        return true
    }

    override fun hashCode(): Int {
        return pseudo.hashCode()
    }
}