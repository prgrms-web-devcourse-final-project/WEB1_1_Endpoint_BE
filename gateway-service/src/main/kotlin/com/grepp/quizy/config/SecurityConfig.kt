package com.grepp.quizy.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig() {

    companion object {
        private val PERMIT_ALL_PATHS = arrayOf(
            "/api/*/api-docs",     // api-docs 경로들
            "/api-docs",
            "/api-docs/**",
            "/v3/api-docs/**",     // OpenAPI 관련
            "/swagger-ui/**",      // Swagger UI
            "/swagger-ui.html",
            "/webjars/**",         // Swagger UI 리소스
            "/favicon.ico",        // favicon
            "/actuator/**"         // actuator endpoint
        )
    }

    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity
    ): SecurityWebFilterChain {
        return http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .authorizeExchange {
                it.pathMatchers(*PERMIT_ALL_PATHS).permitAll()
                    .anyExchange().authenticated()
            }
            .build()
    }
}
