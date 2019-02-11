package com.kebuu.core.board

import com.kebuu.core.Position

class Treasure(
        position: Position,
        val zPoints: Int = 0,
        var hasBeenFound: Boolean = false
) : AbstractBoardItem(position)