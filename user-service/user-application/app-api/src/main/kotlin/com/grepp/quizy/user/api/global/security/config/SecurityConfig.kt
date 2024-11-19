package com.grepp.quizy.user.api.global.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.anyRequest().permitAll()
            }
            .oauth2Login { oAuth2LoginConfigurer ->
                oAuth2LoginConfigurer.authorizationEndpoint {
                    it.baseUri("/api/v1/auth/oauth2/authorize")
                }
                oAuth2LoginConfigurer.redirectionEndpoint {
                    it.baseUri("/api/v1/auth/oauth2/code/*")
                }
            }
            .build()
    }
}