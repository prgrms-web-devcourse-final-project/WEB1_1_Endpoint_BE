package com.grepp.quizy.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.common.exception.CustomException
import java.text.SimpleDateFormat
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
@Order(-2)
class GlobalErrorExceptionHandler : ErrorWebExceptionHandler {
    private val objectMapper =
            ObjectMapper()
                    .registerModule(JavaTimeModule())
                    .disable(
                            SerializationFeature
                                    .WRITE_DATES_AS_TIMESTAMPS
                    )
                    .setDateFormat(
                            SimpleDateFormat(
                                    "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
                            )
                    )

    override fun handle(
            exchange: ServerWebExchange,
            ex: Throwable,
    ): Mono<Void> {
        val bufferFactory = exchange.response.bufferFactory()

        val errorResponse =
                when (ex) {
                    is CustomException -> {
                        exchange.response.statusCode =
                                HttpStatusCode.valueOf(ex.status)
                        ApiResponse.error(
                                ex.errorCode.errorReason,
                                exchange.request.path.value(),
                                ex.message,
                        )
                    }

                    else -> {
                        exchange.response.statusCode =
                                HttpStatus.BAD_GATEWAY
                        ApiResponse.error(
                                HttpStatus.BAD_GATEWAY.name,
                                "게이트웨이 오류",
                        )
                    }
                }

        exchange.response.headers.contentType =
                MediaType.APPLICATION_JSON

        val dataBuffer =
                bufferFactory.wrap(
                        objectMapper.writeValueAsBytes(errorResponse)
                )
        return exchange.response
                .writeWith(Mono.just(dataBuffer))
                .doOnError { err -> exchange.response.setComplete() }
    }
}
