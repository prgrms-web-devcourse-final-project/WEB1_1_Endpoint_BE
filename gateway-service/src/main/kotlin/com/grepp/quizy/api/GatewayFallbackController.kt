package com.grepp.quizy.api

import com.grepp.quizy.exception.CustomCircuitBreakerException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/fallback")
class GatewayFallbackController {
    @GetMapping("/user")
    fun userServiceFallback() {
        throw CustomCircuitBreakerException.UserServiceUnavailableException
    }

    @GetMapping("/quiz")
    fun quizServiceFallback() {
        throw CustomCircuitBreakerException.QuizServiceUnavailableException
    }

    @GetMapping("/game")
    fun gameServiceFallback() {
        throw CustomCircuitBreakerException.GameServiceUnavailableException
    }

    @GetMapping("/ws")
    fun webSocketServiceFallback() {
        throw CustomCircuitBreakerException.WsUnavailableException
    }

    @GetMapping("/matching")
    fun matchingServiceFallback() {
        throw CustomCircuitBreakerException.MatchingServiceUnavailableException
    }
}