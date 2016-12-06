package com.kebuu.core.utils

import com.kebuu.core.Position
import com.kebuu.core.board.spawn.Spawn
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

fun <T> MutableList<CompletableFuture<T>>.waitAndUnwrap(): List<T> {
    CompletableFuture.allOf(*this.toTypedArray()).join()
    return this.map { it.get(0, TimeUnit.NANOSECONDS) }
}

fun Spawn.canMoveTo(position: Position): Boolean {
    return this.position.distanceFrom(position) <= this.attributes.speed
}

operator fun Position.plus(position: Position): Position {
    return this.plusX(position.x).plusY(position.y)
}

fun equalsOnType(one: Any, another: Any?): Boolean {
    return another != null && another.javaClass == one.javaClass
}