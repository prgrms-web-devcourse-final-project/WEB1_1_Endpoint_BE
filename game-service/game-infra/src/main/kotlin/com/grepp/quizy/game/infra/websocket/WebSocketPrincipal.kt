package com.grepp.quizy.game.infra.websocket

import java.security.Principal

class WebSocketPrincipal(
    private val name: String
) : Principal {
    override fun getName(): String {
        return name
    }
}