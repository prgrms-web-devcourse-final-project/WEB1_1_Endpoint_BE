package com.grepp.quizy.game.infra.websocket

import java.security.Principal
import java.util.*
import org.springframework.http.server.ServerHttpRequest
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import java.lang.IllegalArgumentException

class CustomHandshakeHandler : DefaultHandshakeHandler() {

    override fun determineUser(
            request: ServerHttpRequest,
            wsHandler: WebSocketHandler,
            attributes: MutableMap<String, Any>,
    ): Principal = WebSocketPrincipal(
        request.headers.getFirst("X-AUTH-ID")
            ?: throw IllegalArgumentException("X-AUTH-ID is required")
    )

}
