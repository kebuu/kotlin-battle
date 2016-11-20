package com.kebuu.client.service

import com.kebuu.core.board.spawn.SpawnAttributes


interface SpawnService {

    fun spawnUpdate(point: Int): SpawnAttributes
}