package com.grepp.quizy.user.infra.user.messaging.listener

interface EventHandler {
    fun process(event: Event)
}