package com.kebuu

import com.kebuu.core.config.GameConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.social.config.annotation.EnableSocial
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableSocial
open class KotlinBattleApplication {

    lateinit @Autowired var gameConfig: GameConfig

    @Bean
    open fun restTemplate(): RestTemplate {
        return RestTemplate(clientHttpRequestFactory())
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