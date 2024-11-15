package com.grepp.quizy.infra.game.websocket

import org.springframework.http.server.ServerHttpRequest
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import java.security.Principal
import java.util.*

class CustomHandshakeHandler : DefaultHandshakeHandler() {

    override fun determineUser(
        request: ServerHttpRequest,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Principal {
        // TODO: 실제로 게이트웨이에서 사용자 정보를 받는 방식이 정해지면 구현 방식 변경
        return WebSocketPrincipal(UUID.randomUUID().toString())
    }
}
