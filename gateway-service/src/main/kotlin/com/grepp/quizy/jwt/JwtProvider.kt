package com.grepp.quizy.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${jwt.secret}")
    private val secretKey: String
) {
    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body

            !claims.expiration.before(Date())
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getUserId(token: String): String {
        return getSubject(key, token)
    }

    fun getUserRole(token: String): String {
        return getClaims(token)["role"].toString()
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun getSubject(key: SecretKey, token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }
}