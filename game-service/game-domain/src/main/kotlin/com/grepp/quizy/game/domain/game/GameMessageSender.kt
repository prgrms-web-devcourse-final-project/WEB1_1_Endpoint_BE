package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.GameMessage

interface GameMessageSender {
    fun send(userId: String, message: GameMessage)
}
