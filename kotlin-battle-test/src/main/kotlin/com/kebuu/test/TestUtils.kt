
package com.kebuu.test

import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}

fun <T> whenMock(t: T): OngoingStubbing<T> {
    return Mockito.`when`(t)
}

fun <T> uninitialized(): T = null as T

