package com.kebuu.dto

import com.kebuu.Dimension
import com.kebuu.dto.board.item.AbstractBoardItemDto
import com.kebuu.dto.board.item.BoardItemDtoFactory

class BoardDto {

    val dimension: Dimension
    val items: Map<String, List<AbstractBoardItemDto>>

    constructor(board: com.kebuu.board.Board) {
        dimension = board.dimension

        items = board.itemsByPosition().mapValues {
            it.value.map { BoardItemDtoFactory.from(it) }
        }.mapKeys { "${it.key.x}_${it.key.y}" }
    }
}