package com.kebuu.core

data class Dimension(var x: Int = 10, var y: Int = 10){

    fun getNumberOfCells() = x * y
}
