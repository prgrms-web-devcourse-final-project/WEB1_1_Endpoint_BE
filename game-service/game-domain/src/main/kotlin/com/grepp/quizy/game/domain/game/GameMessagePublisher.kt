package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.GameMessage

interface GameMessagePublisher {
    fun publish(message: GameMessage)
}
