package com.kebuu.core.dto

import com.kebuu.core.Position
import com.kebuu.core.board.Board
import com.kebuu.core.board.Treasure
import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.dto.board.item.BoardItemDtoFactory

class GameInfo {

    lateinit var position: Position
    lateinit var board: LightBoardDto
    var lastStepNumber: Int? = null
    var gamerLife: Int = 0
    var gamerZPoints: Int = 0

    constructor()

    constructor(spawn: Spawn, board: Board, currentStep: Int, lastStepNumber: Int? = null) {
        this.position = spawn.position
        this.gamerLife = spawn.owner.getLife()
        this.gamerZPoints = spawn.owner.getZPoints()
        this.board = createLightBoardDto(board, currentStep)
        this.lastStepNumber = if(isFirstStep(currentStep)) lastStepNumber else null
    }

    private fun createLightBoardDto(board: Board, currentStep: Int): LightBoardDto {
        val dimension = if(isFirstStep(currentStep)) board.dimension else null

        val itemsDto = board.items.filter {
            if (isFirstStep(currentStep)) {
                true
            } else {
                it is Spawn || it is Treasure
            }
        }.map { BoardItemDtoFactory.from(it) }


        return LightBoardDto(dimension, itemsDto)
    }

    private fun isFirstStep(currentStep: Int) = currentStep == 1

}