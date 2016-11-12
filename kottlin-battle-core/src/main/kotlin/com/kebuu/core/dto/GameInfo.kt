package com.kebuu.core.dto

import com.kebuu.core.board.Board
import com.kebuu.core.board.spawn.Spawn

class GameInfo(val spawn: Spawn, val board: Board) {

    fun currentPosition() = spawn.position

}