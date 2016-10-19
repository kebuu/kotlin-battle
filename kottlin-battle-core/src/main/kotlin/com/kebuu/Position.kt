package com.kebuu

data class Position(var x: Int = 0, var y: Int = 0){

    companion object {
        val ORIGIN = Position()
    }
}
