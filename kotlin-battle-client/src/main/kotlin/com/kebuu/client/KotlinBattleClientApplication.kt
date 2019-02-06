package com.kebuu.client

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
open class KotlinBattleClientApplication {

    @Bean
    open fun objectMapper(): ObjectMapper = ObjectMapper().apply {
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        disable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
        enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES)
        findAndRegisterModules()
    }

}

fun main(args : Array<String>){
    SpringApplication.run(KotlinBattleClientApplication::class.java, *args)
}