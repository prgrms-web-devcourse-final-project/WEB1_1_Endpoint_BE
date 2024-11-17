package com.grepp.quizy.game.domain

import java.security.Principal

interface GameMessageSender {
    fun send(principal: Principal, message: String)
}