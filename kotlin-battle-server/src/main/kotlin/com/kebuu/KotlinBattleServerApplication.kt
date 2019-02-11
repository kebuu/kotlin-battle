package com.kebuu

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.kebuu.server.config.GameConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.social.config.annotation.EnableSocial
import org.springframework.web.client.RestTemplate
import java.time.Instant
import java.util.*

@SpringBootApplication
@EnableSocial
open class KotlinBattleApplication {

    @Autowired
    lateinit var gameConfig: GameConfig

    @Bean
    open fun restTemplate(): RestTemplate {
        return RestTemplate(clientHttpRequestFactory())
    }

    @Bean
    open fun random(): Random {
        return Random(Instant.EPOCH.epochSecond)
    }

    @Bean
    open fun objectMapper(): ObjectMapper = ObjectMapper().apply {
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        disable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
        enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES)

        findAndRegisterModules()
    }

    private fun clientHttpRequestFactory(): ClientHttpRequestFactory {
        val timeout = gameConfig.gamerResponseTimeoutTimeUnit.toMillis(gameConfig.gamerResponseTimeout).toInt()

        return HttpComponentsClientHttpRequestFactory().apply {
            setReadTimeout(timeout)
            setConnectTimeout(timeout)
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(KotlinBattleApplication::class.java, *args)
}