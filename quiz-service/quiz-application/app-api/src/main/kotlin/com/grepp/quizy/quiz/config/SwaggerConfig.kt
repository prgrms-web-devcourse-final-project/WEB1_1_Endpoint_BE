package com.grepp.quizy.quiz.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
        info =
                io.swagger.v3.oas.annotations.info.Info(
                        title = "Quiz API",
                        version = "v1",
                )
)
class SwaggerConfig {
    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI().addServersItem(Server().url("/"))
    }
}
