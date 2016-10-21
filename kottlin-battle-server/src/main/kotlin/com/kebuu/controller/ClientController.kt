package com.kebuu.controller

import com.kebuu.Board
import com.kebuu.action.ClientAction
import com.kebuu.dto.ClientInfo
import com.kebuu.enums.ClientStatus
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/clients")
internal class ClientController {

    val restTemplate : RestTemplate = RestTemplate()

    @GetMapping
    fun clientInfos(): Array<ClientInfo>  {
        val clientHost = "localhost"
        val clientPort = 8080
        var clientInfo = ClientInfo(clientHost, clientPort, ClientStatus.KO)

        val responseEntity = restTemplate.exchange("http://$clientHost:$clientPort", HttpMethod.GET, HttpEntity.EMPTY, String::class.java)

        if (HttpStatus.OK == responseEntity.statusCode) {
            clientInfo = clientInfo.copy(status = ClientStatus.OK)
        }

        return arrayOf(clientInfo)
    }

    @GetMapping("/{id}/action")
    fun getAction(): ClientAction {
        val clientHost = "localhost"
        val clientPort = 8080

        return restTemplate.exchange("http://$clientHost:$clientPort/action", HttpMethod.GET, HttpEntity.EMPTY, ClientAction::class.java).body
    }

    @GetMapping("/{id}/init")
    fun init(): ClientAction {
        val clientHost = "localhost"
        val clientPort = 8080

        return restTemplate.exchange("http://$clientHost:$clientPort/action", HttpMethod.POST, HttpEntity(Board()), ClientAction::class.java).body
    }
}

