package com.grepp.quizy.game.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckApi {
    @GetMapping("/health")
    fun healthCheck(): String {
        return "I'm Game service"
    }
}
