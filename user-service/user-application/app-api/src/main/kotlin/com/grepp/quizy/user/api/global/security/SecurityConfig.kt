package com.grepp.quizy.user.api.global.security

import com.grepp.quizy.user.api.global.oauth2.CustomOAuth2LoginFailureHandler
import com.grepp.quizy.user.api.global.oauth2.CustomOAuth2LoginSuccessHandler
import com.grepp.quizy.user.api.global.oauth2.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val customOAuth2LoginSuccessHandler: CustomOAuth2LoginSuccessHandler,
    private val customOAuth2LoginFailureHandler: CustomOAuth2LoginFailureHandler
) {

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer { // security를 적용하지 않을 리소스
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .requestMatchers("/error", "/favicon.ico")
        }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.anyRequest().permitAll() // gateway 에서 검증
            }
            .oauth2Login {
                it.userInfoEndpoint { c ->
                    c.userService(customOAuth2UserService)
                }
                    .successHandler(customOAuth2LoginSuccessHandler)
                    .failureHandler(customOAuth2LoginFailureHandler)
            }
            //TODO: exception handling 구현 entry point
            .build()
    }
}