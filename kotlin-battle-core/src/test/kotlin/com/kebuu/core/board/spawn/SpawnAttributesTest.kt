package com.kebuu.core.board.spawn

import org.assertj.core.api.Assertions
import org.junit.Test

class SpawnAttributesTest {

    @Test
    fun updateRandomly() {
        val spawnAttributes = SpawnAttributes()

        val updatedSpawnAttributes = spawnAttributes.updateRandomly(4)
        Assertions.assertThat(spawnAttributes.sumPoints).isEqualTo(4)
        Assertions.assertThat(spawnAttributes).isEqualTo(updatedSpawnAttributes)

        spawnAttributes.updateRandomly(3)
        Assertions.assertThat(spawnAttributes.sumPoints).isEqualTo(7)

    }

}