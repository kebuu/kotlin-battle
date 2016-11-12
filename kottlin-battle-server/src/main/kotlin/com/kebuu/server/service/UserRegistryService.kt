package com.kebuu.server.service

import com.kebuu.server.bean.User
import org.springframework.stereotype.Service

@Service
class UserRegistryService(private val users: MutableMap<String, User>) {

    fun getUser(userId: String) = users[userId]
    fun putUser(user: User) = users[user.userId]


}

