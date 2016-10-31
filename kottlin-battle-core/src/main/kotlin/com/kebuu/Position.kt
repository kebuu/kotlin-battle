package com.kebuu

data class Position(val x: Int = 0, val y: Int = 0): Comparable<Position> {

    companion object {
        val ORIGIN = Position()
    }

    fun plusX(x: Int): Position {
        return this.copy(x = this.x + x)
    }

    fun plusY(y: Int): Position {
        return this.copy(y = this.y + y)
    }

    override fun compareTo(other: Position): Int {
        var comparison = y.compareTo(other.y)

        if (comparison == 0) {
            comparison = x.compareTo(other.x)
        }

        return comparison
    }
}
