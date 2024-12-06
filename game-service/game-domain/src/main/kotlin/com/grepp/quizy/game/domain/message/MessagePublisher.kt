package com.grepp.quizy.game.domain.message

interface MessagePublisher {

    fun publish(message: StreamMessage)

}