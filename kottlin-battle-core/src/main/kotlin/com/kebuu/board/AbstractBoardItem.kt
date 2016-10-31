package com.kebuu.board

import com.kebuu.Position

abstract class AbstractBoardItem(protected var position:Position = Position()): BoardItem {

    override fun position() = position
}