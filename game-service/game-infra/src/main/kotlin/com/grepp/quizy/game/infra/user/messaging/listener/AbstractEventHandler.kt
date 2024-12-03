package com.grepp.quizy.game.infra.user.messaging.listener

import com.fasterxml.jackson.databind.ObjectMapper
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer

abstract class AbstractEventHandler<T>(
    private val mapper: ObjectMapper,
    protected val actions: MutableMap<EventType, Consumer<T>> = ConcurrentHashMap()
) {
    abstract fun initActions()

    fun process(event: Event) {
        val eventType = event.eventType
        actions[eventType]?.accept(event as T)
    }
}