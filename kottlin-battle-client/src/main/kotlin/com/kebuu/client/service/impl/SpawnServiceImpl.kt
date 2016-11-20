package com.kebuu.client.service.impl

import com.kebuu.client.service.SpawnService
import com.kebuu.core.board.spawn.SpawnAttributes
import org.springframework.stereotype.Service

@Service
class SpawnServiceImpl : SpawnService {

    override fun spawnUpdate(point: Int): SpawnAttributes {
        return SpawnAttributes()
    }
}