package com.kebuu.server.dto

import com.kebuu.core.gamer.Gamer
import com.kebuu.core.gamer.RemoteGamer

class GamerDto(val pseudo: String,
               val zPoints: Double,
               val life: Int,
               var avatarUrl: String? = null) { // TODO voir si y a pas moyen de faire mieux que "var"

    constructor(gamer: Gamer) : this(gamer.pseudo(), gamer.getZPoints(), gamer.getLife()) {
        if (gamer is RemoteGamer) {
            avatarUrl = gamer.avatarUrl
        }
    }
}

