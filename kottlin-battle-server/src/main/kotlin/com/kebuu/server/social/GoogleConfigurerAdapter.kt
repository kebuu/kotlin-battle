package com.kebuu.server.social

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer
import org.springframework.social.config.annotation.SocialConfigurerAdapter
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository
import org.springframework.social.google.connect.GoogleConnectionFactory

@Configuration
open class GoogleConfigurerAdapter: SocialConfigurerAdapter()  {

    @Autowired lateinit var properties: GoogleProperties
    @Autowired lateinit var accountConnectionSignUp: AccountConnectionSignUp

    override fun getUsersConnectionRepository(connectionFactoryLocator: ConnectionFactoryLocator): UsersConnectionRepository {
        val inMemoryUsersConnectionRepository = InMemoryUsersConnectionRepository(connectionFactoryLocator)
        inMemoryUsersConnectionRepository.setConnectionSignUp(accountConnectionSignUp)
        return inMemoryUsersConnectionRepository
    }

    override fun addConnectionFactories(configurer: ConnectionFactoryConfigurer?, environment: Environment?) {
        val factory = GoogleConnectionFactory(properties.appId, properties.appSecret)
        factory.scope = "email profile"
        configurer!!.addConnectionFactory(factory)
    }
}
