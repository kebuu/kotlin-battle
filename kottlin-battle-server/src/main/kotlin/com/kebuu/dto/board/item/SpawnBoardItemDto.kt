package com.kebuu.dto.board.item

import com.kebuu.board.spawn.Spawn

class SpawnBoardItemDto: AbstractBoardItemDto {

    val pseudo: String

    constructor(spawn: Spawn): super("spawn") {
        pseudo = spawn.owner.pseudo()
    }

}