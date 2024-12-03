package com.grepp.quizy.game.infra.user.messaging.listener

interface EventHandler {
    fun process(event: Event)
}