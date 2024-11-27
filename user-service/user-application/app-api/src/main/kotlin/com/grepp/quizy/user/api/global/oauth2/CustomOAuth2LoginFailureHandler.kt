package com.grepp.quizy.user.api.global.oauth2

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import java.net.URLEncoder

@Component
class CustomOAuth2LoginFailureHandler(
    @Value("\${frontend.url}")
    private val frontendUrl: String,
) : AuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        val provider = request.requestURI
            .substringAfter("/oauth2/code/")
            .takeIf { it.isNotEmpty() }
            ?: "unknown"

        val errorMessage = URLEncoder.encode(exception.message, "UTF-8")

        response.sendRedirect(
            "${frontendUrl}/oauth/${provider.lowercase()}/callback?error=${errorMessage}"
        )
    }
}