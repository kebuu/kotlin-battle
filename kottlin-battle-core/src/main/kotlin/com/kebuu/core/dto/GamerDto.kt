package com.kebuu.core.dto

import com.kebuu.core.gamer.Gamer
import com.kebuu.core.gamer.RemoteGamer

class GamerDto(val email: String,
               val zPoints: Int,
               val life: Int,
               val avatarUrl: String?) {

    fun getShortName() = email.substringBefore('@')

    constructor(gamer: Gamer) : this(
            gamer.pseudo(),
            gamer.getZPoints(),
            gamer.getLife(),
            if (gamer is RemoteGamer) gamer.avatarUrl else null
    )
}

