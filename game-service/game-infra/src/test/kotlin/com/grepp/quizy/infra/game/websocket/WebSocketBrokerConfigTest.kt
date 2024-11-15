package com.grepp.quizy.infra.game.websocket

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.web.socket.messaging.WebSocketStompClient
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class WebSocketBrokerConfigTest : FunSpec() {

    private val stompClient = mockk<WebSocketStompClient>()
    private val stompSession = mockk<StompSession>()

    init {
        beforeTest {
            every {
                stompClient.connectAsync(any(), any<StompSessionHandlerAdapter>())
            } returns CompletableFuture.completedFuture(stompSession)

            every { stompSession.isConnected } returns true
            every { stompSession.disconnect() } just Runs
            every { stompClient.stop() } just Runs
        }

        afterTest {
            stompSession.disconnect()
            stompClient.stop()
        }

        test("웹소켓 연결") {
            // given
            val url = "ws://localhost:8080/ws"

            // when
            val session = stompClient
                .connectAsync(url, object : StompSessionHandlerAdapter() {})
                .get(60, TimeUnit.SECONDS)

            // then
            session.isConnected shouldBe true
            verify {
                stompClient.connectAsync(url, any<StompSessionHandlerAdapter>())
                session.isConnected
            }
        }
    }
}