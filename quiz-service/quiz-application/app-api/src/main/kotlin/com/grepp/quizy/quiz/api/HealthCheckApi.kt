package com.grepp.quizy.quiz.api

import com.grepp.quizy.common.api.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckApi {
    @GetMapping("/health")
    fun healthCheck(): ApiResponse<Unit> {
        return ApiResponse.success("I'm QUIZ service")
    }
}
