package com.kebuu.core.board.spawn

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SpawnAttributesTest {

    @Test
    fun updateRandomly() {
        val spawnAttributes = SpawnAttributes()

        val updatedSpawnAttributes = spawnAttributes.updateRandomly(4)
        assertThat(spawnAttributes.sumPoints).isEqualTo(4)
        assertThat(spawnAttributes).isEqualTo(updatedSpawnAttributes)

        spawnAttributes.updateRandomly(3)
        assertThat(spawnAttributes.sumPoints).isEqualTo(7)

    }

}