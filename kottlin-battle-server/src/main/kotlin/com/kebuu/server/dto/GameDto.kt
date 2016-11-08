package com.kebuu.server.dto

import com.kebuu.core.game.Game

class GameDto(private val game: Game) {

    fun getCurrentStep() = game.currentStep
    fun getGamers() = game.gamers.map { GamerDto(it) }
    fun getStatus() = game.status
    fun getBoard() = BoardDto(game.board)
}

