package com.kebuu.core.dto

import com.kebuu.core.board.Board
import com.kebuu.core.board.spawn.Spawn

class GameInfo {

    lateinit var spawn: Spawn
    lateinit var board: Board
    lateinit var currentStep: Integer

    constructor() {
    }

    constructor(spawn: Spawn, board: Board, currentStep: Integer) {
        this.spawn = spawn
        this.board = board
        this.currentStep = currentStep
    }

    fun currentPosition() = spawn.position

}