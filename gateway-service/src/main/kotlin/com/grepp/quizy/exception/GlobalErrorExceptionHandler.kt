package com.grepp.quizy.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.common.exception.CustomException
import io.jsonwebtoken.JwtException
import org.slf4j.LoggerFactory
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.cloud.gateway.support.NotFoundException
import org.springframework.cloud.gateway.support.ServiceUnavailableException
import org.springframework.cloud.gateway.support.TimeoutException
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.text.SimpleDateFormat

@Component
@Order(-2)
class GlobalErrorExceptionHandler : ErrorWebExceptionHandler {
    private val objectMapper = ObjectMapper()
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .setDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val bufferFactory = exchange.response.bufferFactory()

        val errorResponse = when (ex) {
            is CustomException -> {
                logger.error("CustomException: ${ex.errorCode.errorReason} - ${ex.message}", ex)
                exchange.response.statusCode = HttpStatusCode.valueOf(ex.status)
                ApiResponse.error(
                    ex.errorCode.errorReason,
                    exchange.request.path.value(),
                    ex.message
                )
            }

            is JwtException -> {
                logger.error("JwtException: ${ex.message}", ex)
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                ApiResponse.error(HttpStatus.UNAUTHORIZED.name, ex.message ?: "토큰이 유효하지 않습니다")
            }

            is NotFoundException -> {
                logger.error("NotFoundException: ${ex.message}", ex)
                exchange.response.statusCode = HttpStatus.NOT_FOUND
                ApiResponse.error(HttpStatus.NOT_FOUND.name, "요청한 리소스를 찾을 수 없습니다")
            }

            is TimeoutException -> {
                logger.error("TimeoutException: ${ex.message}", ex)
                exchange.response.statusCode = HttpStatus.GATEWAY_TIMEOUT
                ApiResponse.error(HttpStatus.GATEWAY_TIMEOUT.name, "서비스 응답 시간 초과")
            }

            is ServiceUnavailableException -> {
                logger.error("ServiceUnavailableException: ${ex.message}", ex)
                exchange.response.statusCode = HttpStatus.SERVICE_UNAVAILABLE
                ApiResponse.error(HttpStatus.SERVICE_UNAVAILABLE.name, "서비스를 사용할 수 없습니다")
            }

            is ResponseStatusException -> {
                logger.error("ResponseStatusException: ${ex.message}", ex)
                exchange.response.statusCode = ex.statusCode
                val message = when (ex.statusCode) {
                    HttpStatus.NOT_FOUND -> "요청한 리소스를 찾을 수 없습니다"
                    HttpStatus.UNAUTHORIZED -> "인증이 필요합니다"
                    HttpStatus.FORBIDDEN -> "접근 권한이 없습니다"
                    HttpStatus.BAD_REQUEST -> "잘못된 요청입니다"
                    HttpStatus.METHOD_NOT_ALLOWED -> "허용되지 않은 HTTP 메서드입니다"
                    HttpStatus.NOT_ACCEPTABLE -> "요청한 미디어 타입을 제공할 수 없습니다"
                    HttpStatus.REQUEST_TIMEOUT -> "요청 시간이 초과되었습니다"
                    HttpStatus.CONFLICT -> "리소스 상태에 충돌이 발생했습니다"
                    HttpStatus.GONE -> "요청한 리소스가 더 이상 사용할 수 없습니다"
                    HttpStatus.PAYLOAD_TOO_LARGE -> "요청 payload가 너무 큽니다"
                    HttpStatus.URI_TOO_LONG -> "요청 URI가 너무 깁니다"
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE -> "지원하지 않는 미디어 타입입니다"
                    HttpStatus.TOO_MANY_REQUESTS -> "너무 많은 요청이 발생했습니다"
                    HttpStatus.INTERNAL_SERVER_ERROR -> "서버 내부 오류가 발생했습니다"
                    HttpStatus.BAD_GATEWAY -> "게이트웨이 오류가 발생했습니다"
                    HttpStatus.SERVICE_UNAVAILABLE -> "서비스를 사용할 수 없습니다"
                    HttpStatus.GATEWAY_TIMEOUT -> "게이트웨이 시간 초과"
                    else -> ex.message ?: "오류가 발생했습니다"
                }
                ApiResponse.error(ex.statusCode.toString(), message)
            }

            else -> {
                logger.error("Unexpected Exception: ${ex.message}", ex)
                exchange.response.statusCode = HttpStatus.BAD_GATEWAY
                ApiResponse.error(HttpStatus.BAD_GATEWAY.name, "게이트웨이 오류")
            }
        }

        exchange.response.headers.contentType = MediaType.APPLICATION_JSON

        val dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(errorResponse))
        return exchange.response.writeWith(Mono.just(dataBuffer))
            .doOnError { err ->
                logger.error("Error writing response: ${err.message}", err)
                exchange.response.setComplete()
            }
    }
}