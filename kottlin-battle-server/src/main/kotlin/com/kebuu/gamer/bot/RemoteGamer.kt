package com.kebuu.gamer.bot

import com.kebuu.action.NoAction
import com.kebuu.action.StepAction
import com.kebuu.board.spawn.SpawnAttributes
import com.kebuu.dto.GameInfo
import com.kebuu.gamer.BaseGamer
import com.kebuu.util.Loggable
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class RemoteGamer private constructor(pseudo: String,
                                      val restTemplate: RestTemplate): BaseGamer(pseudo), Loggable{

    override fun getNextAction(gameInfo: GameInfo): StepAction {
        var action: StepAction = NoAction()

        val supplyAsync: CompletableFuture<ResponseEntity<StepAction>> = CompletableFuture.supplyAsync {
            restTemplate.exchange("", HttpMethod.GET, HttpEntity.EMPTY, StepAction::class.java)
        }

        try {
            val responseEntity = supplyAsync.get(2, TimeUnit.SECONDS)

            if (responseEntity.statusCode == HttpStatus.OK) {
                action = responseEntity.body
            } else {
                logger().info("Erreur...")
            }
        } catch (e: TimeoutException) {
            logger().info("Pas de reponse dans le temps imparti...")
        } catch (e: ExecutionException) {
            logger().info("Erreur...")
        }

        return action
    }

    override fun getSpawnAttributes(point: Int): SpawnAttributes {
        return SpawnAttributes()
    }


}

@Component
@ConfigurationProperties(prefix = "config.server")
class ServerConfig {

    private var remoteGamerTimeoutSecond = 2
}

