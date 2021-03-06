package com.kebuu.core.utils

import com.kebuu.core.Position
import com.kebuu.core.board.spawn.Spawn
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

fun <T> MutableList<CompletableFuture<T>>.waitAndUnwrap(): List<T> {
    CompletableFuture.allOf(*this.toTypedArray()).join()
    return this.map { it.get(0, TimeUnit.NANOSECONDS) }
}

fun Spawn.canMoveTo(position: Position): Boolean =
    position.distanceFrom(position) <= attributes.speed

operator fun Position.plus(position: Position): Position =
    plusX(position.x).plusY(position.y)

fun equalsOnType(one: Any, another: Any?): Boolean =
    another != null && another.javaClass == one.javaClass

fun <T, K> T?.mapTo(transform: (t: T) -> K) =
    if(this == null) null else transform(this)

fun <T> T?.keepIf(predicate: (t: T) -> Boolean) =
    if(this == null) null else if(predicate(this)) this else null