package com.grepp.quizy.jwt

import com.grepp.quizy.exception.CustomJwtException
import com.grepp.quizy.user.RedisTokenRepository
import com.grepp.quizy.user.api.global.util.CookieUtils
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
@Order(-1) // 높은 우선순위
class JwtAuthGlobalFilter(
    private val routeValidator: RouteValidator,
    private val jwtProvider: JwtProvider,
    private val redisTokenRepository: RedisTokenRepository,
    private val jwtValidator: JwtValidator,
) : GlobalFilter {

    override fun filter(
        exchange: ServerWebExchange,
        chain: GatewayFilterChain,
    ): Mono<Void> {
        val request = exchange.request

        if (!routeValidator.isSecured(request)) {
            return chain.filter(exchange)
        }

        if (!request.headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            CookieUtils.getCookieValue(request, "refreshToken").let {
                it ?: throw CustomJwtException.JwtNotFountException
                return addHeader(it, exchange, chain)
            }
        }

        val token = resolveToken(request) ?: throw CustomJwtException.JwtUnsupportedException

        return addHeader(token, exchange, chain)
    }

    private fun resolveToken(request: ServerHttpRequest): String? {
        val authHeader = request.headers[HttpHeaders.AUTHORIZATION]?.get(0) ?: ""
        return if (authHeader.startsWith("Bearer ")) {
            authHeader.substring(7)
        } else {
            null
        }
    }

    private fun addHeader(
        token: String,
        exchange: ServerWebExchange,
        chain: GatewayFilterChain,
    ): Mono<Void> {
        jwtValidator.validateToken(token)
        if (!redisTokenRepository.isAlreadyLogin(token)) {
            throw CustomJwtException.JwtLoggedOutException
        }

        try {
            // 새로운 헤더 맵 생성
            val headers = HttpHeaders()
            // 기존 헤더 복사
            exchange.request.headers.forEach { name, values ->
                headers[name] = values
            }
            // 새로운 헤더 추가
            headers["X-Auth-Id"] = jwtProvider.getUserIdFromToken(token).value.toString()

            // 새로운 요청 객체 생성
            val mutatedRequest = object : ServerHttpRequestDecorator(exchange.request) {
                override fun getHeaders(): HttpHeaders {
                    return headers
                }
            }

            // 수정된 요청으로 교체한 exchange로 체인 실행
            return chain.filter(
                exchange
                    .mutate()
                    .request(mutatedRequest)
                    .build()
            )
        } catch (ex: Exception) {
            throw CustomJwtException.JwtNotValidateException
        }
    }
}

@Component
class RouteValidator {
    private val openApiEndpoints =
        listOf(
            "/oauth2/",
            "/api/auth/",
            "/api/quiz/feed",
            "/api/search",
        )

    fun isSecured(
        request:
        ServerHttpRequest
    ): Boolean {
        return openApiEndpoints.none { path ->
            request.uri.path.contains(path)
        }
    }
}
