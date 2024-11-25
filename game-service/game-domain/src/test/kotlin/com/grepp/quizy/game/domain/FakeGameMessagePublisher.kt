package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.game.GameMessagePublisher

class FakeGameMessagePublisher() : GameMessagePublisher {

    private val messages = mutableListOf<GameMessage>()

    override fun publish(message: GameMessage) {
        messages.add(message)
    }

    fun getMessages(): List<GameMessage> {
        return messages
    }

    fun clear() {
        messages.clear()
    }
}