package com.kebuu.core.dto.board.item

import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.bot.Bot
import com.kebuu.core.gamer.Gamer
import com.kebuu.core.gamer.RemoteGamer
import kotlin.properties.Delegates

class SpawnBoardItemDto: AbstractBoardItemDto {

    var pseudo: String by Delegates.notNull()
    var gamerType: String by Delegates.notNull()
    var iconUrl: String? = null

    constructor()

    constructor(spawn: Spawn) {
        pseudo = spawn.owner.pseudo()
        gamerType = extractGamerType(spawn.owner)
        iconUrl = extractIconUrl(spawn.owner)
    }

    private fun extractIconUrl(gamer: Gamer): String? {
        return if (gamer is RemoteGamer) gamer.avatarUrl else null
    }

    private fun extractGamerType(gamer: Gamer): String {
        return when(gamer) {
            is Bot -> gamer.type
            is RemoteGamer -> "remote"
            else -> throw IllegalArgumentException(gamer.javaClass.toString())
        }
    }
}