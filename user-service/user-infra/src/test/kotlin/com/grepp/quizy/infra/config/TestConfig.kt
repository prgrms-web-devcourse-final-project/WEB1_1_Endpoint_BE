package com.grepp.quizy.infra.config

import com.grepp.quizy.infra.user.repository.UserJPARepository
import com.grepp.quizy.infra.user.repository.UserRepositoryAdaptor
import io.mockk.mockk
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean

@SpringBootConfiguration
@EnableAutoConfiguration
class TestConfig {
    @Bean
    fun userJPARepository(): UserJPARepository = mockk<UserJPARepository>()

    @Bean
    fun userRepositoryAdaptor(userJPARepository: UserJPARepository): UserRepositoryAdaptor {
        return UserRepositoryAdaptor(userJPARepository)
    }
}