package com.kebuu.dto

import com.kebuu.board.spawn.Spawn
import com.kebuu.game.Game

class GameInfo(val spawn: Spawn, val game: Game) {

    fun currentPosition() = spawn.position()

}