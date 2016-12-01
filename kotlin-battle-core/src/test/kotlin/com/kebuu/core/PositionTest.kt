package com.kebuu.core

import org.assertj.core.api.Assertions
import org.junit.Test

class PositionTest {

    @Test
    fun distanceFrom() {
        Assertions.assertThat(Position.ORIGIN.distanceFrom(Position(0, 0))).isEqualTo(0)
        Assertions.assertThat(Position.ORIGIN.distanceFrom(Position(2, 0))).isEqualTo(2)
        Assertions.assertThat(Position.ORIGIN.distanceFrom(Position(0, 2))).isEqualTo(2)
        Assertions.assertThat(Position.ORIGIN.distanceFrom(Position(2, 2))).isEqualTo(4)
        Assertions.assertThat(Position.ORIGIN.distanceFrom(Position(2, 2))).isEqualTo(Position(2, 2).distanceFrom(Position.ORIGIN))
    }

}