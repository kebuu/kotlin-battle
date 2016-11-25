package com.kebuu.server.dto

import com.kebuu.core.Dimension
import com.kebuu.core.board.Board
import com.kebuu.core.dto.board.item.AbstractBoardItemDto
import com.kebuu.core.dto.board.item.BoardItemDtoFactory

class BoardDto {

    val dimension: Dimension
    val items: Map<String, List<AbstractBoardItemDto>>

    constructor(board: Board) {
        dimension = board.dimension

        items = board.itemsByPosition().mapValues {
            it.value.map { BoardItemDtoFactory.from(it) }
        }.mapKeys { "${it.key.x}_${it.key.y}" }
    }
}