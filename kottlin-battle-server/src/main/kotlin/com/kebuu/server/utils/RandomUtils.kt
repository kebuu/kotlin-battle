package com.kebuu.server.utils

import java.time.Instant
import java.util.*

class RandomUtils {

    companion object {
        private val random = Random(Instant.EPOCH.epochSecond)

        fun nextInt() = random.nextInt()
    }


}