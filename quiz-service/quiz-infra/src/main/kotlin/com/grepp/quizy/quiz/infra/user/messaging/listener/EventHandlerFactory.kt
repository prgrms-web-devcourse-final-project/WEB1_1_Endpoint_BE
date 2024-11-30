package com.grepp.quizy.quiz.infra.user.messaging.listener

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class EventHandlerFactory(
    userEventHandler: EventHandler,
    private val handlers: MutableMap<String, EventHandler> = ConcurrentHashMap()
) {

    companion object {
        private const val USER = "user-service"
    }

    init {
        handlers[USER] = userEventHandler
    }

    fun getEventHandler(originService: String): EventHandler {
        return handlers[originService] ?: throw IllegalArgumentException("$originService 에 대한 핸들러가 없습니다.")
    }
}