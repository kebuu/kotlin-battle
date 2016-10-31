package com.kebuu.board.spawn

import com.kebuu.Position
import com.kebuu.board.AbstractBoardItem
import com.kebuu.gamer.Gamer

class Spawn(val owner: Gamer,
            val attributes: SpawnAttributes = SpawnAttributes(),
            position: Position = Position.ORIGIN): AbstractBoardItem(position) {

    fun moveTo(position: Position) {
        this.position = position
    }

}