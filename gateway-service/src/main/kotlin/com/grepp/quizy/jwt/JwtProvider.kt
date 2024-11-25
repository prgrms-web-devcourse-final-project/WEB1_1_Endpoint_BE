package com.grepp.quizy.jwt

import com.grepp.quizy.user.UserId
import com.grepp.quizy.user.api.global.jwt.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.*
import javax.crypto.SecretKey
import org.springframework.stereotype.Component

@Component
class JwtProvider(private val jwtProperties: JwtProperties) {
    private val secretKey: SecretKey =
            Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    fun getUserIdFromToken(token: String): UserId {
        val claims =
                Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .body

        return UserId(claims.subject.toLong())
    }

    fun getUserRoleFromToken(token: String): String {
        val claims =
                Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .body

        return claims["role"] as String
    }

    fun getExpiration(accessToken: String): Long {
        val expiration =
                Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(accessToken)
                        .body
                        .expiration

        val now = Date().time
        return (expiration.time - now)
    }
}
