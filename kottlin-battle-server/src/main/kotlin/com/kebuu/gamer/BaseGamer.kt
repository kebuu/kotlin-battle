package com.kebuu.gamer

abstract class BaseGamer(val pseudo: String): Gamer {

    private var zPoints: Double = 0.0

    override fun getZPoints() = zPoints

    override fun addZPoints(double: Double) {
        zPoints += double
    }

    override fun removeZPoints(double: Double) {
        zPoints -= double
    }

    override fun removeZPointsPercent(percent: Double) {
        zPoints *= (1.0 - percent / 100.0)
    }

    override fun pseudo() = pseudo

}