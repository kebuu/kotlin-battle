package com.kebuu.core.cell

import com.kebuu.core.board.ZPoint
import com.kebuu.core.board.spawn.Spawn

class BoardCell(val spawns: MutableList<Spawn> = mutableListOf(),
                val zpoint: ZPoint? = null,
                val hasHole: Boolean = false,
                val hasMountain: Boolean = false) {

    fun isEmpty(): Boolean {
        return spawns.isEmpty() && !hasHole && !hasMountain && zpoint == null
    }
}

