package com.grepp.quizy.game.infra.websocket

import com.grepp.quizy.game.infra.websocket.WebSocketDestination.*
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration

@Configuration
@EnableWebSocketMessageBroker
class WebSocketBrokerConfig : WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(
        registry: StompEndpointRegistry
    ) {
        registry
            .addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
            .setHandshakeHandler(CustomHandshakeHandler())
            .withSockJS()
    }

    override fun configureWebSocketTransport(registry: WebSocketTransportRegistration) {
        registry
            .setTimeToFirstMessage(0)
            .setSendTimeLimit(0)
            .setMessageSizeLimit(0)
            .setSendBufferSizeLimit(0)
    }

    override fun configureMessageBroker(
        registry: MessageBrokerRegistry
    ) {
        registry.setApplicationDestinationPrefixes(
            APPLICATION_PREFIX.destination
        )
        registry.enableSimpleBroker(
            MULTIPLE_PREFIX.destination,
            SINGLE_PREFIX.destination,
        ).setHeartbeatValue(longArrayOf(0,0))
        registry.setUserDestinationPrefix(USER_PREFIX.destination)
    }
}
