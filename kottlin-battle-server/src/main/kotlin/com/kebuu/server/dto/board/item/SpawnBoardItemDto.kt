package com.kebuu.server.dto.board.item

import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.gamer.Gamer
import com.kebuu.server.gamer.bot.DummyBot
import com.kebuu.server.gamer.bot.ImmobileBot
import com.kebuu.server.gamer.bot.RemoteGamer

class SpawnBoardItemDto: AbstractBoardItemDto {

    val pseudo: String
    val gamerType: String
    val iconUrl: String?

    constructor(spawn: Spawn): super("spawn") {
        pseudo = spawn.owner.pseudo()
        gamerType = extractGamerType(spawn.owner)
        iconUrl = extractIconUrl(spawn.owner)
    }

    private fun extractIconUrl(gamer: Gamer): String? {
        if (gamer is RemoteGamer) {

        }
        return null
    }

    private fun extractGamerType(gamer: Gamer): String {
        return when(gamer) {
            is ImmobileBot -> "immobileBot"
            is DummyBot -> "dummyBot"
            is RemoteGamer -> "remote"
            else -> throw IllegalArgumentException(gamer.javaClass.toString())
        }
    }
}