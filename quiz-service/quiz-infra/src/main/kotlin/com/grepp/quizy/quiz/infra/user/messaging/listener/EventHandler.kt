package com.grepp.quizy.quiz.infra.user.messaging.listener

interface EventHandler {
    fun process(event: Event)
}