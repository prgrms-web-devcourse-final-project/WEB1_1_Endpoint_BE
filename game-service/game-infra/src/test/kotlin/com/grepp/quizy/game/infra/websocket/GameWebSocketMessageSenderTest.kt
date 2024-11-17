package com.grepp.quizy.game.infra.websocket

import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.springframework.messaging.simp.SimpMessagingTemplate
import java.security.Principal

class GameWebSocketMessageSenderTest : DescribeSpec({

    val messageTemplate: SimpMessagingTemplate = mockk()
    val messageSender = GameWebSocketMessageSender(messageTemplate)

    describe("GameWebSocketMessageSender") {
        context("메시지를 보내면") {
            val principal = mockk<Principal>()
            every { principal.name } returns "minhyeok"
            justRun { messageTemplate.convertAndSendToUser(any(), any(), any()) }
            val message = "테스트 메시지"
            messageSender.send(principal, message)
            it("메시지가 사용자에게 전달된다.") {
                verify {
                    messageTemplate.convertAndSendToUser(
                        "minhyeok",
                        "/queue/quiz-grade",
                        message
                    )
                }
            }
        }
    }

})