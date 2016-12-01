package com.kebuu.core.board

import com.kebuu.core.Dimension
import com.kebuu.core.Position
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class BoardTest {

    lateinit var board : Board

    @Before
    fun setUp() {
        board = Board(Dimension(6, 10))
    }

    @Test
    fun isOnBoard() {
        Assertions.assertThat(board.isOnBoard(Position.ORIGIN)).isTrue()
        Assertions.assertThat(board.isOnBoard(Position(5, 5))).isTrue()
        Assertions.assertThat(board.isOnBoard(Position(5, 9))).isTrue()
        Assertions.assertThat(board.isOnBoard(Position(5, 10))).isFalse()
        Assertions.assertThat(board.isOnBoard(Position(5, -5))).isFalse()
        Assertions.assertThat(board.isOnBoard(Position(6, 5))).isFalse()
    }

}