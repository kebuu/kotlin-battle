package com.kebuu.gamer.bot

import java.util.concurrent.atomic.AtomicInteger


interface Bot {

    companion object {
        val COUNTER = AtomicInteger()
    }
}