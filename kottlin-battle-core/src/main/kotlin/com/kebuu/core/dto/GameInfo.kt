package com.kebuu.core.dto

import com.kebuu.core.Position
import com.kebuu.core.board.Board
import com.kebuu.core.board.Treasure
import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.dto.board.item.BoardItemDtoFactory

class GameInfo {

    lateinit var position: Position
    lateinit var board: LightBoardDto
    lateinit var currentStep: Integer
    lateinit var life: Integer

    constructor()

    constructor(spawn: Spawn, board: Board, currentStep: Integer) {
        this.position = spawn.position
        this.life = Integer(spawn.owner.getLife())
        this.board = createLightBoardDto(board, currentStep)
        this.currentStep = currentStep
    }

    private fun createLightBoardDto(board: Board, currentStep: Integer): LightBoardDto {
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

    private fun isFirstStep(currentStep: Integer) = currentStep.toInt() == 1

}