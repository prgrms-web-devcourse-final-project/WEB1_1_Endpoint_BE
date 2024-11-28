package com.grepp.quizy.user.api.global.oauth2

import com.grepp.quizy.user.api.global.jwt.JwtGenerator
import com.grepp.quizy.user.api.global.jwt.JwtProvider
import com.grepp.quizy.user.api.global.util.CookieUtils
import com.grepp.quizy.user.domain.user.RedisTokenRepository
import com.grepp.quizy.user.domain.user.UserLoginManager
import com.grepp.quizy.user.domain.user.UserReader
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class CustomOAuth2LoginSuccessHandler(
    private val jwtGenerator: JwtGenerator,
    private val jwtProvider: JwtProvider,
    private val userReader: UserReader,
    private val redisTokenRepository: RedisTokenRepository,
    private val userLoginManager: UserLoginManager,
    @Value("\${frontend.url}")
    private val frontendUrl: String,
) : SimpleUrlAuthenticationSuccessHandler() {

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
        val accessTokenExpirationTime = jwtProvider.getExpiration(accessToken)
        val refreshTokenExpirationTime = jwtProvider.getExpiration(refreshToken)

        // 이미 로그인 되어있는 유저는 로그인 X
//        try {
//            userLoginManager.login(user.id, accessTokenExpirationTime)
//        } catch (e: CustomUserException) {
//            logger.error("Failed to login user", e)
//            val errorMessage = URLEncoder.encode(e.message, "UTF-8")
//            response.sendRedirect(
//                "${frontendUrl}/oauth/${
//                    customOAuth2User.getProvider().toString().lowercase()
//                }/callback?error=${errorMessage}"
//            )
//            return
//        }
        // TODO: 로그인 중복 처리 일단 막아놓음

        redisTokenRepository.saveRefreshToken(user.id, refreshToken, refreshTokenExpirationTime)

        CookieUtils.addCookie(response, "refreshToken", refreshToken, refreshTokenExpirationTime.toInt())

        response.sendRedirect(
            "${frontendUrl}/oauth/${
                customOAuth2User.getProvider().toString().lowercase()
            }/callback?token=${accessToken}&guest=${user.isGuest()}&refreshTokenExpirationTime=${refreshTokenExpirationTime}"
        )
        logger.info("User ${user.id} logged in successfully")
    }

}