package com.grepp.quizy.user.api.global.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class JwtConfig

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secret: String,
    val accessTokenValidity: Long,
    val refreshTokenValidity: Long,
) {
//    @Value("\${jwt.secret}")
//    lateinit var secret: String
//
//    @Value("\${jwt.access-token-validity}")
//    var accessTokenValidity: Long = 0
//
//    @Value("\${jwt.refresh-token-validity}")
//    var refreshTokenValidity: Long = 0

}