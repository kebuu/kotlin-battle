package com.kebuu.client.service.impl

import com.kebuu.client.service.SpawnService
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.utils.Loggable
import org.springframework.stereotype.Service

@Service
class SpawnServiceImpl : SpawnService, Loggable {

    override fun spawnUpdate(point: Int): SpawnAttributes {
        return SpawnAttributes(0)
    }
}