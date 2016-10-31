package com.kebuu.dto

import com.kebuu.game.Game

class GameDto(private val game: Game) {

    fun getCurrentStep() = game.currentStep
    fun getGamers() = game.gamers
    fun getStatus() = game.status
    fun getBoard() = BoardDto(game.board)
}

