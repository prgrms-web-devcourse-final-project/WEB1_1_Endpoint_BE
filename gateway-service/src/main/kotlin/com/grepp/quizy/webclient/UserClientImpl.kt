package com.grepp.quizy.webclient

import com.grepp.quizy.exception.CustomJwtException
import com.grepp.quizy.user.RedisTokenRepository
import com.grepp.quizy.user.UserId
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class UserClientImpl(
    private val webClient: WebClient,
    private val redisTokenRepository: RedisTokenRepository
) : UserClient {

    @Value("\${service.user.url}")
    private lateinit var BASE_URL: String


    override fun validateUser(userId: Long): Mono<Unit> {
        if (redisTokenRepository.isExistUser(UserId(userId))) {
            return Mono.just(Unit)
        }

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