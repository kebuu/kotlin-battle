package com.kebuu.core.board.spawn

import java.util.*

data class SpawnAttributes(var speed: Int = 1,
                           var force: Int = 1,
                           var resistance: Int = 1,
                           var shootDistance: Int = 1) {

    val sumPoints: Int
        get() = speed + force + resistance + shootDistance

    fun updateRandomly(point: Int): SpawnAttributes {
        val newAttributes = this.copy()

        val operations = listOf(
            { attributes: SpawnAttributes -> attributes.speed += 1},
            { attributes: SpawnAttributes -> attributes.force += 1},
            { attributes: SpawnAttributes -> attributes.resistance += 1},
            { attributes: SpawnAttributes -> attributes.shootDistance += 1}
        )

        for(i in 0..point) {
            Collections.shuffle(operations)
            operations.first()(this)
        }

        return newAttributes
    }

    fun isValid(): Boolean {
        return speed >= 0 &&
                force >= 0 &&
                resistance >= 0 &&
                shootDistance >= 0
    }
}