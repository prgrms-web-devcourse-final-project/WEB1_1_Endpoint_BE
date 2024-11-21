package com.grepp.quizy.jwt

import com.grepp.quizy.exception.CustomJwtException
import com.grepp.quizy.global.RedisUtil
import com.grepp.quizy.user.RedisTokenRepository
import com.grepp.quizy.user.api.global.jwt.JwtProperties
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
    private val redisRepository: RedisTokenRepository
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
                is MalformedJwtException -> throw CustomJwtException.JwtNotValidateException

                is ExpiredJwtException -> throw CustomJwtException.JwtExpriedException
                is UnsupportedJwtException -> throw CustomJwtException.JwtUnsupportedException
                is IllegalArgumentException -> throw CustomJwtException.JwtNotFountException
                else -> throw CustomJwtException.JwtUnknownException
            }
        }
    }

    fun validateRefreshToken(token: String): Boolean {
        if (!validateToken(token)) {
            return false
        }
        val userId = jwtProvider.getUserIdFromToken(token)
        val refreshToken = redisRepository.getRefreshToken(userId) ?: return false

        return refreshToken == token
    }
}