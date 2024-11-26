package com.grepp.quizy.matching.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.headers.Header
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
        info =
                io.swagger.v3.oas.annotations.info.Info(
                        title = "Matching API",
                        version = "v1",
                )
)
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        // Bearer 토큰 인증 스키마 설정
        val securityScheme = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER)
            .name("Authorization")

        // 토큰 요구사항 설정
        val securityRequirement = SecurityRequirement()
            .addList("bearerAuth")

        // x-auth-id 헤더 설정을 포함한 Components 구성
        val components = Components()
            .addSecuritySchemes("bearerAuth", securityScheme)
            .addHeaders(
                "x-auth-id", Header()
                    .description("사용자 ID (게이트웨이에서 자동 추가)")
                    .schema(StringSchema())
            )

        return OpenAPI()
            .components(components)
            .security(listOf(securityRequirement))  // Arrays.asList 대신 listOf 사용
            .addServersItem(Server().url("/"))
            .info(
                io.swagger.v3.oas.models.info.Info()
                    .title("게이트웨이 API 문서")
                    .description("게이트웨이에서 JWT 토큰을 검증하고 x-auth-id 헤더를 자동으로 추가합니다")
                    .version("1.0")
            )
    }
}
