package com.grepp.quizy.infra.jwt

import com.grepp.quizy.domain.user.UserId
import com.grepp.quizy.infra.jwt.exception.*
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import javax.crypto.SecretKey

class JwtProvider(
    private val jwtProperties: JwtProperties
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(
        jwtProperties.secret.toByteArray()
    )

    fun getUserIdFromToken(token: String): UserId {
        val claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body

        return UserId(claims.subject.toLong())
    }

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