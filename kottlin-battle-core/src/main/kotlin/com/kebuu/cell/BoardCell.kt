package com.kebuu.cell

import com.kebuu.board.ZPoint
import com.kebuu.board.spawn.Spawn

class BoardCell(val spawns: MutableList<Spawn> = mutableListOf(),
                val zpoint: ZPoint? = null,
                val hasHole: Boolean = false,
                val hasMountain: Boolean = false) {

    fun isEmpty(): Boolean {
        return spawns.isEmpty() && !hasHole && !hasMountain && zpoint == null
    }
}

