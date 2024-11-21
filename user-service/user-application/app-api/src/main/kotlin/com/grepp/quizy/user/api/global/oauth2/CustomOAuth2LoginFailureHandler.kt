package com.grepp.quizy.user.api.global.oauth2

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.grepp.quizy.common.api.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat

@Component
class CustomOAuth2LoginFailureHandler : AuthenticationFailureHandler {
    private val objectMapper = ObjectMapper()
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .setDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))



    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()

        val apiResponse = when (exception) {
            // TODO: OAuth2 예외에 따른 처리 추가하기
            else -> {
                ApiResponse.error(HttpStatus.UNAUTHORIZED.name, "로그인 실패")
            }
        }

        val writer = response.writer
        writer.write(objectMapper.writeValueAsString(apiResponse))
        writer.flush()
    }
}