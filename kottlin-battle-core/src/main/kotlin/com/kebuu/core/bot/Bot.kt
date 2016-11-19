package com.kebuu.core.bot

import com.kebuu.core.gamer.Gamer
import java.util.concurrent.atomic.AtomicInteger


interface Bot : Gamer {

    val type : String

    companion object {
        val COUNTER = AtomicInteger()
    }
}