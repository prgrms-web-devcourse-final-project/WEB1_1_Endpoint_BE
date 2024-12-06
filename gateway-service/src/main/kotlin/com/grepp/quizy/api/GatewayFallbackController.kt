package com.grepp.quizy.api

import com.grepp.quizy.common.api.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/fallback")
class GatewayFallbackController {
    @GetMapping("/user")
    fun userServiceFallback(): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(ApiResponse.error(HttpStatus.SERVICE_UNAVAILABLE.name, "User service is temporarily unavailable"))
    }

    @GetMapping("/quiz")
    fun quizServiceFallback(): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(ApiResponse.error(HttpStatus.SERVICE_UNAVAILABLE.name, "Quiz service is temporarily unavailable"))
    }

    @GetMapping("/game")
    fun gameServiceFallback(): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(ApiResponse.error(HttpStatus.SERVICE_UNAVAILABLE.name, "Game service is temporarily unavailable"))
    }

    @GetMapping("/ws")
    fun webSocketServiceFallback(): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(
                ApiResponse.error(
                    HttpStatus.SERVICE_UNAVAILABLE.name,
                    "Game webSocket service is temporarily unavailable"
                )
            )
    }

    @GetMapping("/matching")
    fun matchingServiceFallback(): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(ApiResponse.error(HttpStatus.SERVICE_UNAVAILABLE.name, "Matching service is temporarily unavailable"))
    }
}