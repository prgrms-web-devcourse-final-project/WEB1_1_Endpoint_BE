package com.grepp.quizy.webclient

import reactor.core.publisher.Mono

interface UserClient {
    fun validateUser(userId: Long): Mono<Unit>
}