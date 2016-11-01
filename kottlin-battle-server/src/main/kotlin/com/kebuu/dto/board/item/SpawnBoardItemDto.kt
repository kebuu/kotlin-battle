package com.kebuu.dto.board.item

import com.kebuu.board.spawn.Spawn
import com.kebuu.gamer.Gamer
import com.kebuu.gamer.bot.DummyBot
import com.kebuu.gamer.bot.ImmobileBot
import com.kebuu.gamer.bot.RemoteGamer

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