package com.kebuu.util

import org.slf4j.LoggerFactory


interface Loggable {

    fun logger() = LoggerFactory.getLogger(this.javaClass)
}