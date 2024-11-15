package com.grepp.quizy.domain.game

import java.security.Principal

interface GameMessageSender {
    fun send(principal: Principal, message: String)
}