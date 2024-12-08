package com.grepp.quizy.config

import com.grepp.quizy.jwt.JwtProvider
import com.grepp.quizy.user.api.global.util.CookieUtils
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import reactor.core.publisher.Mono

@Configuration
class RateLimiterConfig(
    private val jwtProvider: JwtProvider,
) {

    @Bean
    fun userKeyResolver(): KeyResolver {
        return KeyResolver { exchange ->
            // JWT 토큰에서 사용자 ID를 추출하여 rate limit key로 사용
            val token = extractToken(exchange.request)

            if (token != null) {
                try {
                    val userId = jwtProvider.getUserIdFromToken(token)
                    Mono.just(userId.value.toString())
                } catch (e: Exception) {
                    // 토큰이 유효하지 않은 경우 IP 주소를 key로 사용
                    Mono.just(exchange.request.remoteAddress?.address?.hostAddress ?: "anonymous")
                }

            } else {
                // 토큰이 없는 경우 IP 주소를 key로 사용
                Mono.just(exchange.request.remoteAddress?.address?.hostAddress ?: "anonymous")
            }
        }
    }

    private fun extractToken(request: ServerHttpRequest): String? {
        return if (request.headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            resolveToken(request)
        } else {
            CookieUtils.getCookieValue(request, "refreshToken") ?: ""
        }
    }

    private fun resolveToken(request: ServerHttpRequest): String? {
        val authHeader = request.headers[HttpHeaders.AUTHORIZATION]?.get(0) ?: ""
        return if (authHeader.startsWith("Bearer ")) {
            authHeader.substring(7)
        } else {
            null
        }
    }
}