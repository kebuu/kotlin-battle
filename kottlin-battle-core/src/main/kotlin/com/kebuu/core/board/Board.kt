package com.kebuu.core.board

import com.kebuu.core.Dimension
import com.kebuu.core.Position
import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.gamer.Gamer
import java.util.*
import kotlin.comparisons.naturalOrder

data class Board(val dimension: Dimension = Dimension(),
                 val items: MutableList<BoardItem> = mutableListOf()): Iterable<Position> {

    fun setDimension(dimension: Dimension) {
        this.dimension.x = dimension.x
        this.dimension.y = dimension.y
    }

    fun itemsByPosition(): SortedMap<Position, List<BoardItem>> {
        return items.groupBy { it.position }.toSortedMap(naturalOrder())
    }

    fun isOnBoard(position: Position): Boolean {
        return (0..dimension.x).contains(position.x) && (0..dimension.y).contains(position.y)
    }

    fun nextEmptyPosition(): Position {
        val itemsByPosition = itemsByPosition()

        return this.firstOrNull { itemsByPosition[it] == null } ?: Position.ORIGIN
    }

    fun randomEmptyPosition(): Position {
        val positions = this.iterator().asSequence().toList()
        Collections.shuffle(positions)

        val itemsByPosition = itemsByPosition()
        return positions.firstOrNull { itemsByPosition[it] == null } ?: Position.ORIGIN
    }

    fun addItem(boardItem: BoardItem) {
        items.add(boardItem)
    }

    override fun iterator(): Iterator<Position> {
        val positions = mutableListOf<Position>()

        for(i in 0..dimension.y) {
            for(j in 0..dimension.x) {
                positions.add(Position(j, i))
            }
        }

        return positions.iterator()
    }

    fun  gamerSpawn(gamer: Gamer): Spawn {
        return items.first { it is Spawn && it.owner == gamer } as Spawn
    }
}

