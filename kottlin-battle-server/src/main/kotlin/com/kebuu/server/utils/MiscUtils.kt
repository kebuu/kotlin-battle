package com.kebuu.server.utils

import com.kebuu.server.bean.User
import org.springframework.security.authentication.AbstractAuthenticationToken
import java.security.Principal

fun Principal.toUser() : User {
    if (this is AbstractAuthenticationToken) {
        val userDetails = this.principal
        return userDetails as? User ?: throw IllegalStateException("C'est quoi ce type de UserDetails tout pourri : ${userDetails.javaClass}")
    } else throw IllegalStateException("C'est quoi ce type de Principal tout pourri : ${this.javaClass}")
}