package com.grepp.quizy.game.domain

interface GameMessagePublisher {
    fun publish(message: Message)
}