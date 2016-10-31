package com.kebuu.controller

import com.kebuu.action.StepAction
import com.kebuu.bean.ClientInfo
import com.kebuu.board.Board
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
    fun getAction(): StepAction {
        val clientHost = "localhost"
        val clientPort = 8080

        return restTemplate.exchange("http://$clientHost:$clientPort/action", HttpMethod.GET, HttpEntity.EMPTY, StepAction::class.java).body
    }

    @GetMapping("/{id}/init")
    fun init(): StepAction {
        val clientHost = "localhost"
        val clientPort = 8080

        return restTemplate.exchange("http://$clientHost:$clientPort/action", HttpMethod.POST, HttpEntity(Board()), StepAction::class.java).body
    }
}

