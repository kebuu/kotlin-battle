package com.kebuu.server.gamer.bot

import com.kebuu.core.action.StepAction
import com.kebuu.core.board.spawn.SpawnAttributes
import com.kebuu.core.dto.GameInfo
import com.kebuu.server.gamer.BaseGamer
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.security.core.GrantedAuthority
import org.springframework.social.security.SocialUserDetails
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

class RemoteGamer constructor(pseudo: String,
                                      val host: String,
                                      val port: Int,
                                      val avatarUrl: String? = null,
                                      val restTemplate: RestTemplate): BaseGamer(pseudo), SocialUserDetails {

    override fun doGetNextAction(gameInfo: GameInfo): StepAction {
        val responseEntity = restTemplate.exchange("http://$host:$$port/actions/next", HttpMethod.PUT, HttpEntity(gameInfo), StepAction::class.java)
        return responseEntity.body
    }

    override fun doGetSpawnAttributes(point: Int): SpawnAttributes {
        val uriBuilder = UriComponentsBuilder.fromHttpUrl("http://$host:$$port/spawn/update").queryParam("point", point)
        val responseEntity = restTemplate.exchange(uriBuilder.build(true).toUri(), HttpMethod.PUT, HttpEntity.EMPTY, SpawnAttributes::class.java)
        return responseEntity.body
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getUsername() = pseudo
    override fun isCredentialsNonExpired() = false
    override fun getUserId() = username
    override fun isAccountNonExpired() = false
    override fun isAccountNonLocked() = true
    override fun isEnabled() = true
    override fun getPassword() = null
}

