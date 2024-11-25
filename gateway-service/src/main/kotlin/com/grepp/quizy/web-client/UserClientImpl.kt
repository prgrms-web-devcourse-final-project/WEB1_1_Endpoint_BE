package com.grepp.quizy.web

import com.grepp.quizy.exception.CustomJwtException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class UserClientImpl(
    private val webClient: WebClient
) : UserClient {

    companion object {
        private const val BASE_URL = "http://localhost:8085"
    }

    // status code를 체크하고 예외를 던지는 방식 유지
    override fun validateUser(userId: Long): Mono<Unit> {
        return webClient.get()
            .uri("$BASE_URL/api/internal/user/validate/$userId")
            .retrieve()
            .toEntity(Unit::class.java)
            .handle<Unit> { response, sink ->
                when (response.statusCode) {
                    HttpStatus.OK -> sink.next(Unit)
                    HttpStatus.UNAUTHORIZED -> sink.error(CustomJwtException.JwtUnknownException)
                    else -> sink.error(CustomJwtException.NotExistUserException)
                }
            }
    }
}