package com.kebuu.core.dto.board.item

import com.kebuu.core.Position

class TreasureBoardItemDto(position: Position = Position.ORIGIN, val zPoints: Int = 0): AbstractBoardItemDto(position)

