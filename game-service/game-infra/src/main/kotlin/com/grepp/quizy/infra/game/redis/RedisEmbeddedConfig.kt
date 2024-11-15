package com.grepp.quizy.infra.game.redis

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer

@Profile("local", "test")
@Configuration
class RedisEmbeddedConfig {

    @Value("\${spring.redis.port}")
    private val redisPort: Int = 6379

    private var redisServer: RedisServer? = null

    @PostConstruct
    private fun start() {
        val port = if (isRedisRunning()) findAvailablePort() else redisPort
        redisServer = RedisServer(port)
        redisServer?.start()
    }

    @PreDestroy
    private fun stop() {
        redisServer?.stop()
    }

    private fun isRedisRunning(): Boolean {
        return isRunning(executeGrepProcessCommand(redisPort))
    }

    fun findAvailablePort(): Int {
        for (port in 10000..65535) {
            val process = executeGrepProcessCommand(port)
            if (!isRunning(process)) {
                return port
            }
        }
        throw IllegalArgumentException("Not Found Available port: 10000 ~ 65535")
    }

    private fun executeGrepProcessCommand(port: Int): Process {
        val command = "netstat -nat | grep LISTEN|grep $port"
        val shell = arrayOf("/bin/sh", "-c", command)
        return Runtime.getRuntime().exec(shell)
    }

    private fun isRunning(process: Process): Boolean {
        var pidInfo = StringBuilder()
        try {
            process.inputStream.bufferedReader().use { reader ->
                reader.forEachLine { line ->
                    pidInfo.append(line)
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }
        return pidInfo.isNotEmpty()
    }

}
