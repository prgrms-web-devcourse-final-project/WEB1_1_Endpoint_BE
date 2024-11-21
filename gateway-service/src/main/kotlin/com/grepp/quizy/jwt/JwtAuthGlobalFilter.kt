package com.grepp.quizy.jwt

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
@Order(-1) // 높은 우선순위
class JwtAuthGlobalFilter(
        private val routeValidator: RouteValidator,
        private val jwtProvider: JwtProvider,
) : GlobalFilter {

    private val logger =
            LoggerFactory.getLogger(JwtAuthGlobalFilter::class.java)

    override fun filter(
            exchange: ServerWebExchange,
            chain: GatewayFilterChain,
    ): Mono<Void> {
        val request = exchange.request

        if (routeValidator.isSecured(request)) {
            if (
                    !request.headers.containsKey(
                            HttpHeaders.AUTHORIZATION
                    )
            ) {
                return onError(
                        exchange,
                        "No authorization header",
                        HttpStatus.UNAUTHORIZED,
                )
            }

            val authHeader =
                    request.headers[HttpHeaders.AUTHORIZATION]?.get(0)
                            ?: ""
            if (!authHeader.startsWith("Bearer ")) {
                return onError(
                        exchange,
                        "Invalid authorization header",
                        HttpStatus.UNAUTHORIZED,
                )
            }

            val token = authHeader.substring(7)

            if (!jwtProvider.validateToken(token)) {
                return onError(
                        exchange,
                        "Invalid JWT token",
                        HttpStatus.UNAUTHORIZED,
                )
            }

            logger.info("token: $token")

            try {
                // 새로운 헤더 맵 생성
                val headers = HttpHeaders()
                // 기존 헤더 복사
                request.headers.forEach { name, values ->
                    headers[name] = values
                }
                // 새로운 헤더 추가
                headers["X-Auth-Id"] = jwtProvider.getUserId(token)

                // 새로운 요청 객체 생성
                val mutatedRequest =
                        object : ServerHttpRequestDecorator(request) {
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
                logger.error(
                        "Error occurred while processing JWT token",
                        ex,
                )
                return onError(
                        exchange,
                        "Invalid JWT token",
                        HttpStatus.UNAUTHORIZED,
                )
            }
        }

        return chain.filter(exchange)
    }

    private fun onError(
            exchange: ServerWebExchange,
            message: String,
            status: HttpStatus,
    ): Mono<Void> {
        val response = exchange.response
        response.statusCode = status
        logger.error(message)
        return response.setComplete()
    }
}

@Component
class RouteValidator {
    private val openApiEndpoints =
            listOf(
                    "/api/auth/",
                    "/api/quiz/feed",
                    "/api/search",
                    "/ping",
            )

    fun isSecured(
            request:
                    org.springframework.http.server.reactive.ServerHttpRequest
    ): Boolean {
        return openApiEndpoints.none { path ->
            request.uri.path.contains(path)
        }
    }
}
