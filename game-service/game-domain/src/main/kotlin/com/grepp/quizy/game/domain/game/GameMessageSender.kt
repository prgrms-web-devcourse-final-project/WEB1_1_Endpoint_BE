package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.GameMessage
import java.security.Principal

interface GameMessageSender {
    fun send(principal: Principal, message: GameMessage)
}
