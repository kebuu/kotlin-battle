package com.kebuu.server.social

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties("spring.social.google")
@Component
open class GoogleProperties {

    var appId = ""
    var appSecret = ""
}