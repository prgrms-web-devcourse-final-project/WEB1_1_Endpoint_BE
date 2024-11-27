package com.grepp.quizy.global

import com.grepp.quizy.exception.CustomJwtException
import com.grepp.quizy.jwt.JwtProvider
import com.grepp.quizy.jwt.JwtValidator
import com.grepp.quizy.user.RedisTokenRepository
import com.grepp.quizy.user.api.global.util.CookieUtils
import com.grepp.quizy.web.UserClient
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
@Order(-1)
class AuthGlobalFilter(
    private val routeValidator: RouteValidator,
    private val jwtProvider: JwtProvider,
    private val redisTokenRepository: RedisTokenRepository,
    private val jwtValidator: JwtValidator,
    private val userClient: UserClient,
) : GlobalFilter {

    override fun filter(
        exchange: ServerWebExchange,
        chain: GatewayFilterChain,
    ): Mono<Void> {
        val request = exchange.request

        // JWT 토큰 추출 시도
        val token = extractToken(request)

        return when {
            // 토큰이 있는 경우 - 경로와 관계없이 무조건 검증
            token != null -> {
                addHeader(token, exchange, chain)
            }
            // 토큰이 없고 Unsecured 경로인 경우
            routeValidator.isUnsecured(request) -> {
                chain.filter(exchange)
            }
            // 토큰이 없고 Secured 경로인 경우
            else -> {
                Mono.error(CustomJwtException.JwtNotFountException)
            }
        }
    }

    private fun extractToken(request: ServerHttpRequest): String? = try {
        if (request.headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            resolveToken(request)
        } else {
            CookieUtils.getCookieValue(request, "refreshToken")
        }
    } catch (e: Exception) {
        null
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
        val userId = jwtProvider.getUserIdFromToken(token).value

        // 실제로 존재하는 유저인지 검증이 성공하면 헤더를 추가하고 체인 실행
        return userClient.validateUser(userId)
            .then(Mono.defer {
                val headers = HttpHeaders()
                exchange.request.headers.forEach { name, values ->
                    headers[name] = values
                }
                headers["X-Auth-Id"] = userId.toString()

                val mutatedRequest = object : ServerHttpRequestDecorator(exchange.request) {
                    override fun getHeaders(): HttpHeaders = headers
                }

                chain.filter(
                    exchange
                        .mutate()
                        .request(mutatedRequest)
                        .build()
                )
            })
    }
}

@Component
class RouteValidator {
    private val openApiEndpoints = listOf(
        "/oauth2",
        "/api/quiz/feed",
        "/api/quiz/search",
        "/api/user/info/"
    )

    fun isUnsecured(request: ServerHttpRequest): Boolean {
        if (request.uri.path.endsWith("api-docs")) {
            return true
        }
        return openApiEndpoints.any { path ->
            request.uri.path.contains(path)
        }
    }
}
