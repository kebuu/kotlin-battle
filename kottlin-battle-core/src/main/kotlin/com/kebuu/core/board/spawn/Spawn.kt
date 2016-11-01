package com.kebuu.core.board.spawn

import com.kebuu.core.Position
import com.kebuu.core.board.AbstractBoardItem
import com.kebuu.core.gamer.Gamer

class Spawn(val owner: Gamer,
            val attributes: SpawnAttributes = SpawnAttributes(),
            position: Position = Position.ORIGIN): AbstractBoardItem(position) {

    fun moveTo(position: Position) {
        this.position = position
    }

}