package com.grepp.quizy.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
class WebConfig : WebFluxConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedOrigins(
                "http://localhost:3000",
                "http://localhost:5173"
            )
            .allowedMethods(
                "DELETE",
                "GET",
                "POST",
                "PATCH",
                "PUT",
                "OPTIONS"
            )
            .allowedHeaders(
                "Access-Control-Allow-Headers",
                "Access-Control-Allow-Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers",
                "Origin",
                "Cache-Control",
                "Content-Type",
                "Authorization",
            )
            .exposedHeaders("*") // CORS 응답에 대해 클라이언트가 접근할수있도록 허용
            .allowCredentials(true)
            .maxAge(3600)
    }
}
