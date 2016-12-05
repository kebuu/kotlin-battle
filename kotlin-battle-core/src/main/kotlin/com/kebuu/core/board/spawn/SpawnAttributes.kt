package com.kebuu.core.board.spawn

import java.util.*

data class SpawnAttributes(var speed: Int = 0,
                           var force: Int = 0,
                           var resistance: Int = 0,
                           var shootDistance: Int = 0) {

    constructor(point: Int): this(point, point, point, point)

    val sumPoints: Int
        get() = speed + force + resistance + shootDistance

    fun updateRandomly(point: Int): SpawnAttributes {
        val operations = listOf(
            { attributes: SpawnAttributes -> attributes.speed += 1},
            { attributes: SpawnAttributes -> attributes.force += 1},
            { attributes: SpawnAttributes -> attributes.resistance += 1},
            { attributes: SpawnAttributes -> attributes.shootDistance += 1}
        )

        for(i in 1..point) {
            Collections.shuffle(operations)
            operations.first()(this)
        }

        return this
    }

    fun isValid(): Boolean {
        return speed >= 0 &&
                force >= 0 &&
                resistance >= 0 &&
                shootDistance >= 0
    }
}