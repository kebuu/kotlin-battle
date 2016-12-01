package com.kebuu.core

fun <T> T?.filterBy(predicate: (T) -> Boolean): T? {
    return this?.let { if(predicate(it)) it else null }
}

fun <T, R> T?.mapBy(function: (T) -> R?): R? {
    return this?.let { function(it) }
}