package com.kebuu.server.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.social.security.SpringSocialConfigurer

@Configuration
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        val springSocialConfigurer = SpringSocialConfigurer()

        http.antMatcher("/**")
                .authorizeRequests().antMatchers("/", "/signin/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
        .apply(springSocialConfigurer)
    }

}