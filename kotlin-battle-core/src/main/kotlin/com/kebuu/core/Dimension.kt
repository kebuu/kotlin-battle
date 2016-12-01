package com.kebuu.core

data class Dimension(var x: Int = 10, var y: Int = 10) : Iterable<Position>{

    val numberOfCells: Int
        get() = x * y

    override fun iterator(): Iterator<Position> {
        val positions = mutableListOf<Position>()

        for (i in 0..(x-1)) {
            for (j in 0..(y-1)) {
                positions.add(Position(i, j))
            }
        }

        return positions.iterator()
    }
}
