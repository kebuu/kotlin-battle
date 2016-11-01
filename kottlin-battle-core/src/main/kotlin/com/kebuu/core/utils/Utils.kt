package com.kebuu.core.utils

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

fun <T> MutableList<CompletableFuture<T>>.waitAndUnwrap(): List<T> {
    CompletableFuture.allOf(*this.toTypedArray()).join()
    return this.map { it.get(0, TimeUnit.NANOSECONDS) }
}