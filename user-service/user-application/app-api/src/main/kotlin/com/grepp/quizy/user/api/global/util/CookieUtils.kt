package com.grepp.quizy.user.api.global.util

import jakarta.servlet.http.HttpServletResponse

object CookieUtils {
    fun addCookie(response: HttpServletResponse, name: String, value: String, maxAge: Int) {
        response.addHeader(
            "Set-Cookie",
            "$name=$value; " +
                    "Path=/; " +
                    "Max-Age=$maxAge; " +
                    "HttpOnly; Secure; " +
                    "SameSite=None"
        )
    }

    fun deleteCookie(response: HttpServletResponse, name: String) {
        response.addHeader(
            "Set-Cookie",
            "$name=; " +
                    "Path=/; " +
                    "Max-Age=0; " +
                    "HttpOnly; Secure; " +
                    "SameSite=None"
        )
    }
}