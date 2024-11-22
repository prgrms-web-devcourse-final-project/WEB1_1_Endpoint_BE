package com.grepp.quizy.user.api.global.jwt

import com.grepp.quizy.user.domain.user.TokenGenerator
import com.grepp.quizy.user.domain.user.User
import com.grepp.quizy.user.domain.user.UserId
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtGenerator(
    private val jwtProperties: JwtProperties,
    private val jwtProvider: JwtProvider
) : TokenGenerator {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(
        jwtProperties.secret.toByteArray()
    )

    override fun generateAccessToken(user: User): String {
        return generateToken(
            claims = mutableMapOf(
                "role" to user.role
            ),
            subject = user.id,
            expirationTime = jwtProperties.accessTokenValidity
        )
    }

    override fun generateRefreshToken(user: User): String {
        return generateToken(
            claims = mutableMapOf(
                "role" to user.role
            ),
            subject = user.id,
            expirationTime = jwtProperties.refreshTokenValidity
        )
    }

    private fun generateToken(
        claims: MutableMap<String, Any>,
        subject: UserId,
        expirationTime: Long
    ): String {
        val now = Date()
        val expiration = Date(now.time + expirationTime)

        return Jwts.builder()
            .setSubject(subject.value.toString())
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(secretKey)
            .compact()
    }

}