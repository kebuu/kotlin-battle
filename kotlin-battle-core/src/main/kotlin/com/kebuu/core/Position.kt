package com.kebuu.core

data class Position(val x: Int = 0, val y: Int = 0) : Comparable<Position> {

    companion object {
        val ORIGIN = Position()
    }

    fun plusX(x: Int): Position = copy(x = this.x + x)

    fun plusY(y: Int): Position = copy(y = this.y + y)

    fun distanceFrom(position: Position): Int = Math.abs(x - position.x) + Math.abs(y - position.y)

    override fun compareTo(other: Position): Int {
        var comparison = x.compareTo(other.x)

        if (comparison == 0) {
            comparison = y.compareTo(other.y)
        }

        return comparison
    }

    override fun toString(): String = "[$x-$y]"
}
