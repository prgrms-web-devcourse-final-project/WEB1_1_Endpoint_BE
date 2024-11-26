package com.grepp.quizy.web

import reactor.core.publisher.Mono

interface UserClient {
    fun validateUser(userId: Long): Mono<Unit>
}