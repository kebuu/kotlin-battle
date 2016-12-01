package com.kebuu.core.cell

import com.kebuu.core.board.Treasure
import com.kebuu.core.board.spawn.Spawn

class BoardCell(val spawns: MutableList<Spawn> = mutableListOf(),
                val treasure: Treasure? = null,
                val hasHole: Boolean = false,
                val hasMountain: Boolean = false) {

    fun isEmpty(): Boolean {
        return spawns.isEmpty() && !hasHole && !hasMountain && treasure == null
    }
}

