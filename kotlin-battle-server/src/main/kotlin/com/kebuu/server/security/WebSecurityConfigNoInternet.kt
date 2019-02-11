package com.kebuu.server.security

import com.kebuu.core.constant.ROLE_ADMIN
import com.kebuu.server.service.UserRegistryService
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService


@Configuration
@Profile("no-internet")
open class WebSecurityConfigNoInternet(val userDetailService: UserDetailsService, val userRegistryService: UserRegistryService) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().antMatchers("/", "/signin/**", "/games/profile", "/games/config").permitAll()
                .antMatchers("/games/new", "/games/start", "/games/stop", "/games/resume").hasRole(ROLE_ADMIN)
                .anyRequest().authenticated()
                .and().csrf().disable()
                .formLogin().defaultSuccessUrl("/", true)
                .and().logout().addLogoutHandler { _, _, authentication ->
                    authentication?.let { userRegistryService.remove(authentication.name)  }
                }
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailService)
    }

}