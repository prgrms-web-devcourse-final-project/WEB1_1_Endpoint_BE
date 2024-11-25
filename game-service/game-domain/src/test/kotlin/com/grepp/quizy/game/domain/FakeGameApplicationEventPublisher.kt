package com.grepp.quizy.game.domain

import org.springframework.context.ApplicationEventPublisher

class FakeGameApplicationEventPublisher : ApplicationEventPublisher {

    private val events = mutableListOf<Any>()

    override fun publishEvent(event: Any) {
        events.add(event)
    }

    fun getEvents(): List<Any> {
        return events
    }

    fun clear() {
        events.clear()
    }

}
