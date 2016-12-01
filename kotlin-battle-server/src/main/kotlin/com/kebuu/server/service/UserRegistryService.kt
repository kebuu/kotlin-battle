package com.kebuu.server.service

import com.kebuu.server.bean.User
import org.springframework.stereotype.Service

@Service
class UserRegistryService {

    private val users = mutableMapOf<String, User>()

    fun getUser(email: String) = users[email]
    
    fun putUser(user: User) {
        users[user.email] = user
    }

    fun remove(name: String) {
        users.remove(name)
    }
}

