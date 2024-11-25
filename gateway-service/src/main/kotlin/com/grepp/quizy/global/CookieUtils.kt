package com.grepp.quizy.user.api.global.util

import org.springframework.http.server.reactive.ServerHttpRequest

object CookieUtils {
    fun getCookieValue(
            request: ServerHttpRequest,
            name: String,
    ): String? {
        val cookies = request.cookies[name]
        return cookies?.firstOrNull()?.value
    }
}
