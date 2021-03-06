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
    var gameStepDurationSecond: Long = 1
    var updateSpawnInterval: Int = 5
    var zPointPercentTakenOnKill: Int = 30
    var zPointPercentLostOnKill: Int = 15
    var zPointPercentLostOnHole: Int = 20
    var gamerLife: Int = 10
    var initialZPoints: Int = 100
    var adminMail = ""
}