package com.grepp.quizy.game.infra.user.messaging.listener

import com.grepp.quizy.game.infra.user.messaging.listener.user.UserEventHandler
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class EventHandlerFactory(
    private val userEventHandler: UserEventHandler,
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