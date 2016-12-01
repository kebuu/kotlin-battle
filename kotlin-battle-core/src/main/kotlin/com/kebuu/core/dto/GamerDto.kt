package com.kebuu.core.dto

import com.kebuu.core.gamer.Gamer
import com.kebuu.core.gamer.RemoteGamer

class GamerDto(val gamerId: String,
               val zPoints: Int,
               val life: Int,
               val shortName: String,
               val avatarUrl: String?) {

    constructor(gamer: Gamer) : this(
            gamer.gamerId(),
            gamer.getZPoints(),
            gamer.getLife(),
            gamer.gamerId().substringBefore('@'),
            if (gamer is RemoteGamer) gamer.avatarUrl else null
    )
}

