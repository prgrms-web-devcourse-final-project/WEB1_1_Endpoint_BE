package com.grepp.quizy.matching.infra.feign.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
@EnableFeignClients(basePackages = ["com.grepp.quizy.matching.infra"])
@EnableAsync
class FeignConfig {
    @Bean
    fun asyncExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 10
        executor.queueCapacity = 25
        executor.threadNamePrefix = "AsyncThread-"
        executor.initialize()
        return executor
    }
}