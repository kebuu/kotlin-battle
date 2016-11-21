package com.kebuu.core.gamer

import com.kebuu.core.action.StepAction
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.dto.GameInfo
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestOperations
import org.springframework.web.util.UriComponentsBuilder

class RemoteGamer constructor(gamerId: String,
                              val host: String,
                              val port: Int,
                              val avatarUrl: String? = null,
                              val restOperations: RestOperations): BaseGamer(gamerId) {

    override fun doGetNextAction(gameInfo: GameInfo): StepAction {
        val ip = if(isIpv6(host)) "[$host]" else host
        val responseEntity = restOperations.exchange("http://$ip:$port/actions/next", HttpMethod.PUT, HttpEntity(gameInfo), StepAction::class.java)
        return responseEntity.body
    }

    override fun doGetSpawnAttributes(point: Int): SpawnAttributes {
        val ip = if(isIpv6(host)) "[$host]" else host
        val uriBuilder = UriComponentsBuilder.fromHttpUrl("http://$ip:$port/spawn/update").queryParam("point", point)
        val responseEntity = restOperations.exchange(uriBuilder.build(true).toUri(), HttpMethod.PUT, HttpEntity.EMPTY, SpawnAttributes::class.java)
        return responseEntity.body
    }

    private fun isIpv6(host: String) = host.contains(":")
}

