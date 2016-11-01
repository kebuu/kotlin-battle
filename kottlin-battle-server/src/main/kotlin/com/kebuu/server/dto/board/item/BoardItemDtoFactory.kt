package com.kebuu.server.dto.board.item

import com.kebuu.core.board.BoardItem
import com.kebuu.core.board.Hole
import com.kebuu.core.board.Mountain
import com.kebuu.core.board.ZPoint
import com.kebuu.core.board.spawn.Spawn

// TODO revoir cette classe toute pourrie
object BoardItemDtoFactory {

    fun from(boardItem: BoardItem): AbstractBoardItemDto {
        return when(boardItem) {
            is Mountain -> MountainBoardItemDto()
            is Hole -> HoleBoardItemDto()
            is ZPoint -> TreasureBoardItemDto()
            is Spawn -> SpawnBoardItemDto(boardItem)
            else -> throw IllegalArgumentException()
        }
    }
}