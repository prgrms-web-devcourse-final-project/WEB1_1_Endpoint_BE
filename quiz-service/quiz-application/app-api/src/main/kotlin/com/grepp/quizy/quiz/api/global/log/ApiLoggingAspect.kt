package com.grepp.quizy.quiz.api.global.log

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class ApiLoggingAspect(private val request: HttpServletRequest) {
    private val log = KotlinLogging.logger {}

    @Pointcut("@annotation(com.grepp.quizy.quiz.api.global.log.LogApi)")
    fun logApi() {}

    @Before("logApi()")
    fun logBefore() {
        val params = request.parameterNames
        val uri = request.requestURI
        val logMessage = StringBuilder("API Request $uri { params:  { ")

        while (params.hasMoreElements()) {
            val name = params.nextElement()
            val value = request.getParameter(name)
            logMessage.append(name).append(": ").append(value).append(" ")
        }
        logMessage.append(" }").append(" }")

        log.info { logMessage.toString() }
    }
}