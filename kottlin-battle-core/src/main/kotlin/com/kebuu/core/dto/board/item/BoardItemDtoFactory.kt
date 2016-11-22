package com.kebuu.core.dto.board.item

import com.kebuu.core.board.BoardItem
import com.kebuu.core.board.Hole
import com.kebuu.core.board.Mountain
import com.kebuu.core.board.Treasure
import com.kebuu.core.board.spawn.Spawn

object BoardItemDtoFactory {

    fun from(boardItem: BoardItem): AbstractBoardItemDto {
        return when(boardItem) {
            is Mountain -> MountainBoardItemDto(boardItem.position)
            is Hole -> HoleBoardItemDto(boardItem.position)
            is Treasure -> TreasureBoardItemDto(boardItem.position, boardItem.zPoints)
            is Spawn -> SpawnBoardItemDto(boardItem)
            else -> throw IllegalArgumentException()
        }
    }
}