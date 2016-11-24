package com.kebuu.core.board.spawn

import com.kebuu.core.action.StepAction
import com.kebuu.core.dto.GameInfo
import com.kebuu.core.gamer.BaseGamer
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class SpawnTest {

    lateinit var spawn: Spawn

    @Before
    fun setUp() {
        spawn = Spawn(TestGamer, SpawnAttributes(5))
    }

    @Test
    fun validateUpdate() {
        Assertions.assertThat(spawn.validateUpdate(SpawnAttributes(4), 2).isOk()).isFalse()
        Assertions.assertThat(spawn.validateUpdate(SpawnAttributes(6), 2).isOk()).isFalse()
        Assertions.assertThat(spawn.validateUpdate(SpawnAttributes(-1), 2).isOk()).isFalse()
        Assertions.assertThat(spawn.validateUpdate(SpawnAttributes(6), 4).isOk()).isTrue()
    }

    @Test
    fun updateRandomly() {
        for(i in 0..20) {
            val point = 6
            val newSpawnAttributes = SpawnAttributes(point)
            Assertions.assertThat(spawn.validateUpdate(newSpawnAttributes, point).isOk()).isTrue()
        }
    }

}

object TestGamer: BaseGamer("test") {
    override fun doGetNextAction(gameInfo: GameInfo): StepAction {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doGetSpawnAttributes(point: Int): SpawnAttributes {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}