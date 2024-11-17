package com.grepp.quizy.infra.jwt

import com.grepp.quizy.domain.user.User
import com.grepp.quizy.domain.user.UserId
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtGenerator(
    private val jwtProperties: JwtProperties
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(
        jwtProperties.secret.toByteArray()
    )

    fun generateAccessToken(user: User): String {
        return generateToken(
            claims = mutableMapOf(
                "role" to user.getRole()
            ),
            subject = user.getId(),
            expirationTime = jwtProperties.accessTokenValidity
        )
    }

    fun generateRefreshToken(user: User): String {
        return generateToken(
            claims = mutableMapOf(),
            subject = user.getId(),
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