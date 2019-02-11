package com.kebuu.server.dto

import com.kebuu.core.Dimension
import com.kebuu.core.board.Board
import com.kebuu.core.dto.board.item.AbstractBoardItemDto
import com.kebuu.core.dto.board.item.BoardItemDtoFactory

class BoardDto(board: Board) {

    val dimension: Dimension = board.dimension

    val items: Map<String, List<AbstractBoardItemDto>> =
        board.itemsByPosition().mapValues {
            it.value.map { item -> BoardItemDtoFactory.from(item) }
        }.mapKeys { "${it.key.x}_${it.key.y}" }

}