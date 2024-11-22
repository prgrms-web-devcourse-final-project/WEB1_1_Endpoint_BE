package com.grepp.quizy.user.api.auth

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.user.api.global.jwt.dto.TokenResponse
import com.grepp.quizy.user.api.global.util.CookieUtils
import com.grepp.quizy.user.domain.user.UserId
import com.grepp.quizy.user.domain.user.UserLogoutUseCase
import com.grepp.quizy.user.domain.user.UserReissueUseCase
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthApi(
    private val userLogoutUseCase: UserLogoutUseCase,
    private val userReissueUseCase: UserReissueUseCase
) {

    @GetMapping("/logout")
    fun logout(
        @RequestHeader("Authorization") accessToken: String,
        @RequestHeader("X-Auth-Id") userId: String,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ApiResponse<Unit> {
        userLogoutUseCase.logout(UserId(userId.toLong()), accessToken.substring(7))
        CookieUtils.deleteCookie(request, response, "refreshToken")
        return ApiResponse.success()
    }

    @PostMapping("/reissue")
    fun reissue(
        @RequestHeader("X-Auth-Id") userId: String,
        response: HttpServletResponse
    ): ApiResponse<TokenResponse> {
        val reissueToken = userReissueUseCase.reissue(UserId(userId.toLong()))
        CookieUtils.addCookie(
            response,
            "refreshToken",
            reissueToken.refreshToken,
            reissueToken.refreshTokenExpirationTime.toInt()
        )
        return ApiResponse.success(TokenResponse.from(reissueToken))
    }
}