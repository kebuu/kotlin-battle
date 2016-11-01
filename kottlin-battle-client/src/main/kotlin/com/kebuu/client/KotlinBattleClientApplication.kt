package com.kebuu.client

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class KotlinBattleClientApplication {


}

fun main(args : Array<String>){
    SpringApplication.run(KotlinBattleClientApplication::class.java, *args)
}