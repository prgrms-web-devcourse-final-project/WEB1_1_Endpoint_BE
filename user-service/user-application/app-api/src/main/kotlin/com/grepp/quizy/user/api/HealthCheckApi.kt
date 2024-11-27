package com.grepp.quizy.user.api

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.web.annotation.AuthUser
import com.grepp.quizy.web.dto.UserPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class HealthCheckApi {

    @GetMapping("/health")
    fun healthCheck(
        @AuthUser principal: UserPrincipal
    ): ApiResponse<Unit> {
        return ApiResponse.success(
            "I'm USER service. UserId : ${principal.value}"
        )
    }

    @GetMapping("/ping")
    fun healthCheck2(): ApiResponse<Unit> {
        return ApiResponse.success("pong")
    }
}
