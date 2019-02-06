package com.kebuu.core.board

import com.kebuu.core.Dimension
import com.kebuu.core.Position
import com.kebuu.core.board.spawn.Spawn
import com.kebuu.core.gamer.Gamer
import java.util.*
import kotlin.comparisons.naturalOrder

data class Board(
        val dimension: Dimension = Dimension(),
        val items: MutableList<BoardItem> = mutableListOf()
) : Iterable<Position> {

    fun setDimension(dimension: Dimension) {
        this.dimension.x = dimension.x
        this.dimension.y = dimension.y
    }

    fun itemsByPosition(): SortedMap<Position, List<BoardItem>> =
        items.groupBy { it.position }.toSortedMap(naturalOrder())

    fun isOnBoard(position: Position): Boolean =
        (0..(dimension.x - 1)).contains(position.x) &&
        (0..(dimension.y - 1)).contains(position.y)

    fun itemsAt(position: Position): List<BoardItem> =
        itemsByPosition()[position] ?: emptyList()

    fun nextEmptyPosition(): Position =
        firstOrNull { itemsByPosition()[it] == null }
        ?: Position.ORIGIN

    fun randomEmptyPosition(): Position {
        val positions = dimension.iterator().asSequence().toList()
        Collections.shuffle(positions)

        val itemsByPosition = itemsByPosition()
        return positions.firstOrNull { itemsByPosition[it] == null } ?: Position.ORIGIN
    }

    fun addItem(boardItem: BoardItem) {
        items.add(boardItem)
    }

    override fun iterator(): Iterator<Position> = dimension.iterator()

    fun gamerSpawn(gamer: Gamer): Spawn =
        items.first { it is Spawn && it.owner == gamer } as Spawn

    fun getTreasureAt(position: Position): Treasure? =
        itemsAt(position).firstOrNull { it is Treasure } as? Treasure

    inline fun <reified T> doesPositionHasItemOfType(position: Position): Boolean =
        itemsAt(position).any { it is T }

    fun cleanFoundTreasures() {
        val itemIterator = items.iterator()
        while (itemIterator.hasNext()) {
            val item = itemIterator.next()

            if (item is Treasure && item.hasBeenFound) {
                itemIterator.remove()
            }
        }
    }
}

