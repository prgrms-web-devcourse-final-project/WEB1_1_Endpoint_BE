package com.grepp.quizy.user.infra.feign

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
@EnableFeignClients(basePackages = ["com.grepp.quizy.user.infra.feign"])
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