package com.kebuu.core.dto

import com.kebuu.core.Dimension
import com.kebuu.core.dto.board.item.AbstractBoardItemDto

class LightBoardDto {

    var dimension: Dimension? = null
    val boardItems: MutableList<AbstractBoardItemDto> = mutableListOf()

    constructor()

    constructor(dimension: Dimension? = null, boardItems: List<AbstractBoardItemDto> = listOf()) {
        this.dimension = dimension
        this.boardItems.addAll(boardItems)
    }
}