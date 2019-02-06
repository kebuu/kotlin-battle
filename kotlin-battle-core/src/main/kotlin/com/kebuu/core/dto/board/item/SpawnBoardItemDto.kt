package com.kebuu.core.dto.board.item

import com.kebuu.core.Position
import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.bot.Bot
import com.kebuu.core.gamer.Gamer
import com.kebuu.core.gamer.RemoteGamer
import kotlin.properties.Delegates

class SpawnBoardItemDto: AbstractBoardItemDto {

    var gamerId: String by Delegates.notNull()
    var gamerType: String by Delegates.notNull()
    var gamerShortName: String by Delegates.notNull()
    var spawnAttributes: SpawnAttributes by Delegates.notNull()
    var iconUrl: String? = null

    constructor(): super(Position.ORIGIN)

    constructor(spawn: Spawn): super(spawn.position) {
        gamerId = spawn.owner.gamerId()
        gamerShortName = spawn.owner.shortName()
        gamerType = extractGamerType(spawn.owner)
        spawnAttributes = spawn.attributes
        iconUrl = extractIconUrl(spawn.owner)
    }

    private fun extractIconUrl(gamer: Gamer): String? =
        if (gamer is RemoteGamer) gamer.avatarUrl else null

    private fun extractGamerType(gamer: Gamer): String = when(gamer) {
        is Bot -> gamer.type
        is RemoteGamer -> "remote"
        else -> throw IllegalArgumentException(gamer.javaClass.toString())
    }
}