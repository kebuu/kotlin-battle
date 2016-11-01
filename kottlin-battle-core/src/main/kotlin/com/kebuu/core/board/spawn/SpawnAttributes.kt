package com.kebuu.core.board.spawn

import java.util.*

data class SpawnAttributes(var speed: Int = 1,
                      var force: Int = 1,
                      var resistance: Int = 1) {

    fun updateRandomly(point: Int): SpawnAttributes {
        val newAttributes = this.copy()

        val operations = listOf(
            { attributes: SpawnAttributes -> attributes.speed += 1},
            { attributes: SpawnAttributes -> attributes.force += 1},
            { attributes: SpawnAttributes -> attributes.resistance += 1}
        )

        for(i in 0..point) {
            Collections.shuffle(operations)
            operations.first()(this)
        }

        return newAttributes
    }

}