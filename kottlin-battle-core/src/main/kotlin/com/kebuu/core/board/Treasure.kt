package com.kebuu.core.board

import com.kebuu.core.Position

class Treasure(position: Position, val zPoints: Int = 0, var discoveredAtStep: Int? = null): AbstractBoardItem(position) {

    fun hasBeenDiscoveredBeforeStep(currentStep: Int): Boolean = discoveredAtStep != null && discoveredAtStep!! < currentStep

}