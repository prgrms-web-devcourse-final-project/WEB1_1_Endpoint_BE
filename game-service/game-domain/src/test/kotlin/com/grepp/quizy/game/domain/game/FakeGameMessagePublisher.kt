package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.GameMessage

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