package com.grepp.quizy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class QuizyGatewayApplication

fun main(args: Array<String>) {
    runApplication<QuizyGatewayApplication>(*args)
}