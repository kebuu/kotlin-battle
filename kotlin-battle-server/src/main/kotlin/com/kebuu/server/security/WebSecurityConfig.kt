package com.kebuu.server.security

import com.kebuu.core.constant.ROLE_ADMIN
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.social.security.SpringSocialConfigurer

@Configuration
@Profile("!no-internet")
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        val springSocialConfigurer = SpringSocialConfigurer()

        http.authorizeRequests().antMatchers("/", "/signin/**", "/games/profile", "/games/config").permitAll()
                .antMatchers("/games/new", "/games/start", "/games/stop", "/games/resume").hasRole(ROLE_ADMIN)
                .anyRequest().authenticated()
                .and().csrf().disable()
                .logout().logoutSuccessUrl("/").permitAll().and()
        .apply(springSocialConfigurer)
    }

}