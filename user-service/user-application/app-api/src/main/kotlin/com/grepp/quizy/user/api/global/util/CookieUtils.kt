package com.grepp.quizy.user.api.global.util

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.util.*

object CookieUtils {
    fun getCookie(request: HttpServletRequest, name: String): Cookie? {
        return request.cookies?.find { it.name == name }
    }

    fun addCookie(response: HttpServletResponse, name: String, value: String, maxAge: Int) {
        val cookie = Cookie(name, value)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.maxAge = maxAge
        response.addCookie(cookie)
    }

    fun deleteCookie(request: HttpServletRequest, response: HttpServletResponse, name: String) {
        request.cookies?.find { it.name == name }?.let {
            it.value = ""
            it.path = "/"
            it.maxAge = 0
            response.addCookie(it)
        }
    }

    fun serialize(obj: Any): String {
        return Base64.getUrlEncoder()
            .encodeToString(ObjectMapper().writeValueAsBytes(obj))
    }

    fun <T> deserialize(cookie: Cookie, cls: Class<T>): T {
        return ObjectMapper().readValue(
            Base64.getUrlDecoder().decode(cookie.value),
            cls
        )
    }
}