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

    lateinit @Autowired var gameConfig: GameConfig

    @Bean
    open fun restTemplate(): RestTemplate {
        return RestTemplate(clientHttpRequestFactory())
    }

    @Bean
    open fun random(): Random {
        return Random(Instant.EPOCH.epochSecond)
    }

    @Bean
    open fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        objectMapper.disable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
        objectMapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES)

        objectMapper.findAndRegisterModules()
        return objectMapper
    }

    private fun clientHttpRequestFactory(): ClientHttpRequestFactory {
        val timeout = gameConfig.gamerResponseTimeoutTimeUnit.toMillis(gameConfig.gamerResponseTimeout).toInt()

        val factory = HttpComponentsClientHttpRequestFactory()
        factory.setReadTimeout(timeout)
        factory.setConnectTimeout(timeout)
        return factory
    }
}

fun main(args : Array<String>){
    SpringApplication.run(KotlinBattleApplication::class.java, *args)
}