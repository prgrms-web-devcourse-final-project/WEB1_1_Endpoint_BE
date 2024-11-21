package com.grepp.quizy.user.api.global.jwt

import com.grepp.quizy.user.api.global.jwt.exception.*
import com.grepp.quizy.user.infra.redis.repository.RedisTokenRepositoryAdaptor
import com.grepp.quizy.user.infra.redis.util.RedisUtil
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Component
class JwtValidator(
    private val jwtProperties: JwtProperties,
    private val jwtProvider: JwtProvider,
    private val redisUtil: RedisUtil,
    private val redisRepository: RedisTokenRepositoryAdaptor
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(
        jwtProperties.secret.toByteArray()
    )

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            return true
        } catch (ex: Exception) {
            when (ex) {
                is SecurityException,
                is MalformedJwtException -> throw JwtNotValidateException

                is ExpiredJwtException -> throw JwtExpriedException
                is UnsupportedJwtException -> throw JwtUnsupportedException
                is IllegalArgumentException -> throw JwtNotFountException
                else -> throw JwtUnkownException
            }
        }
    }
}