package com.grepp.quizy.game.infra.feign

import feign.codec.Encoder
import feign.codec.ErrorDecoder
import feign.form.spring.SpringFormEncoder
import org.springframework.beans.factory.ObjectFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableFeignClients(basePackages = ["com.grepp.quizy.game.infra.feign"])
class FeignConfig {

    @Bean
    @ConditionalOnMissingBean(value = [ErrorDecoder::class])
    fun commonFeignErrorDecoder(): FeignErrorDecoder = FeignErrorDecoder()

    @Bean
    fun encoder(converters: ObjectFactory<HttpMessageConverters>): Encoder =
        SpringFormEncoder(SpringEncoder(converters))

}