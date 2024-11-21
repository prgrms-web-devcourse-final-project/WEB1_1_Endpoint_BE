package com.grepp.quizy.user.api

import com.grepp.quizy.common.api.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class HealthCheckApi {

    @GetMapping("/health")
    fun healthCheck(
            @RequestHeader("X-Auth-Id") userId: String
    ): ApiResponse<Unit> {
        return ApiResponse.success(
                "I'm USER service. UserId : ${userId.toLong()}"
        )
    }

    @GetMapping("/ping")
    fun healthCheck2(): ApiResponse<Unit> {
        return ApiResponse.success("pong")
    }
}
