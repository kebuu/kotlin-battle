package com.kebuu.server.config

import com.kebuu.core.action.LimitedUseAction
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
@ConfigurationProperties(prefix = "game.config")
class GameConfig {

    var gamerResponseTimeout = 1500L
    var gamerResponseTimeoutTimeUnit = TimeUnit.MILLISECONDS
    var maxNumberOfStep = 10
    var limitedActionAllowedOccurrence = mutableMapOf<LimitedUseAction.LimitedUseActionType, Int>()
    val gameStepDurationSecond: Long = 1
    val updateSpawnInterval: Int = 15
    val zPointPercentTakenOnKill: Int = 15
    val zPointPercentLostOnKill: Int = 5
    val zPointPercentLostOnHole: Int = 10
    val gamerLife: Int = 20
}