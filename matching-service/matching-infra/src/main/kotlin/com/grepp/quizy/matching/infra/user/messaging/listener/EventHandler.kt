package com.grepp.quizy.matching.infra.user.messaging.listener

interface EventHandler {
    fun process(event: Event)
}