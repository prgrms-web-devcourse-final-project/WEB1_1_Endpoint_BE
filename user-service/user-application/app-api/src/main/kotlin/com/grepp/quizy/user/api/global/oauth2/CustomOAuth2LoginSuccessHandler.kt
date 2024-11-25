package com.grepp.quizy.user.api.global.oauth2

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.user.api.global.jwt.JwtGenerator
import com.grepp.quizy.user.api.global.jwt.JwtProvider
import com.grepp.quizy.user.api.global.jwt.dto.TokenResponse
import com.grepp.quizy.user.api.global.util.CookieUtils
import com.grepp.quizy.user.domain.user.RedisTokenRepository
import com.grepp.quizy.user.domain.user.UserLoginManager
import com.grepp.quizy.user.domain.user.UserReader
import com.grepp.quizy.user.domain.user.exception.CustomUserException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat

@Component
class CustomOAuth2LoginSuccessHandler(
    private val jwtGenerator: JwtGenerator,
    private val jwtProvider: JwtProvider,
    private val userReader: UserReader,
    private val redisTokenRepository: RedisTokenRepository,
    private val userLoginManager: UserLoginManager
) : SimpleUrlAuthenticationSuccessHandler() {

    private val objectMapper = ObjectMapper()
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .setDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {

        if (response.isCommitted) {
            logger.debug("Response has already been committed")
            return
        }

        val customOAuth2User = authentication.principal as CustomOAuth2User
        val user = userReader.read(customOAuth2User.getEmail())


        val accessToken = jwtGenerator.generateAccessToken(user)
        val refreshToken = jwtGenerator.generateRefreshToken(user)
        val refreshTokenExpirationTime = jwtProvider.getExpiration(refreshToken)

        // 이미 로그인 되어있는 유저는 로그인 X
        try {
            userLoginManager.login(user.id, refreshTokenExpirationTime)
        } catch (e: CustomUserException) {
            logger.error("Failed to login user", e)
            val apiResponse =
                ApiResponse.error(e.errorCode.errorReason.errorCode, e.message)

            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.characterEncoding = StandardCharsets.UTF_8.name()
            response.writer.write(objectMapper.writeValueAsString(apiResponse))
            return
        }

        redisTokenRepository.saveRefreshToken(user.id, refreshToken, refreshTokenExpirationTime)

        CookieUtils.addCookie(response, "refreshToken", refreshToken, refreshTokenExpirationTime.toInt())

        clearAuthenticationAttributes(request)

        val apiResponse =
            ApiResponse.success(TokenResponse(accessToken, refreshToken, refreshTokenExpirationTime, user.isGuest()))

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.writer.write(objectMapper.writeValueAsString(apiResponse))
    }

}