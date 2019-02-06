package com.kebuu.core.board

import com.kebuu.core.Dimension
import com.kebuu.core.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class BoardTest {

    lateinit var board: Board

    @Before
    fun setUp() {
        board = Board(Dimension(6, 10))
    }

    @Test
    fun isOnBoard() {
        assertThat(board.isOnBoard(Position.ORIGIN)).isTrue()
        assertThat(board.isOnBoard(Position(5, 5))).isTrue()
        assertThat(board.isOnBoard(Position(5, 9))).isTrue()
        assertThat(board.isOnBoard(Position(5, 10))).isFalse()
        assertThat(board.isOnBoard(Position(5, -5))).isFalse()
        assertThat(board.isOnBoard(Position(6, 5))).isFalse()
    }

}