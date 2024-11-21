package com.grepp.quizy.search.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
                .addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods(
                        "DELETE",
                        "GET",
                        "POST",
                        "PATCH",
                        "PUT",
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
                        "X-Auth-Id"
                )
                .exposedHeaders("*") // CORS 응답에 대해 클라이언트가 접근할수있도록 허용
                .allowCredentials(true)
                .maxAge(3600)
    }
}
