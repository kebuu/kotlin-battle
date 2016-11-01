package com.kebuu.core.dto

import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.game.Game

class GameInfo(val spawn: Spawn, val game: Game) {

    fun currentPosition() = spawn.position

}