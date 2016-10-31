package com.kebuu.gamer

abstract class BaseGamer(val pseudo: String, var zpoints: Double = 0.0): Gamer {

    override fun getZpointCount() = zpoints

    override fun addZpoint(double: Double) {
        zpoints += double
    }

    override fun remove(double: Double) {
        zpoints -= double
    }

    override fun removePercent(percent: Double) {
        zpoints *= (1.0 - percent / 100.0)
    }

    override fun pseudo() = pseudo

}