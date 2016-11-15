package com.kebuu.server.security

import com.kebuu.core.constant.KotlinBattleConstant
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.social.security.SpringSocialConfigurer

@Configuration
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        val springSocialConfigurer = SpringSocialConfigurer()

        http.authorizeRequests().antMatchers("/", "/signin/**").permitAll()
                .antMatchers("/games/new", "/games/start", "/games/stop").hasRole(KotlinBattleConstant.ROLE_ADMIN)
                .anyRequest().authenticated()
                .and().csrf().disable()
        .apply(springSocialConfigurer)
    }

}