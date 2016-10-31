package com.kebuu.dto.board.item

import com.kebuu.board.BoardItem
import com.kebuu.board.Hole
import com.kebuu.board.Mountain
import com.kebuu.board.ZPoint
import com.kebuu.board.spawn.Spawn

// TODO revoir cette classe toute pourrie
object BoardItemDtoFactory {

    fun from(boardItem: BoardItem): AbstractBoardItemDto{
        return when(boardItem) {
            is Mountain -> MountainBoardItemDto()
            is Hole -> HoleBoardItemDto()
            is ZPoint -> TreasureBoardItemDto()
            is Spawn -> SpawnBoardItemDto(boardItem)
            else -> throw IllegalArgumentException()
        }
    }
}