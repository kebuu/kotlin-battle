package com.kebuu.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
@ConfigurationProperties(prefix = "game.config")
class GameConfig {

    var gamerResponseTimeout = 1500L
    var gamerResponseTimeoutTimeUnit = TimeUnit.MILLISECONDS
    var maxNumberOfStep = 3
}