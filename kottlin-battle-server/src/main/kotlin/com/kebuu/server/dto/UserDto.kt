package com.kebuu.server.dto

import org.springframework.security.core.GrantedAuthority

class UserDto(val username: String, val email: String, val authorities: List<GrantedAuthority>)

