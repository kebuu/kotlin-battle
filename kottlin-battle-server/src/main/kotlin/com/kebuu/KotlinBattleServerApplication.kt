package com.kebuu

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class KotlinBattleApplication {


}

fun main(args : Array<String>){
    SpringApplication.run(KotlinBattleApplication::class.java, *args)
}