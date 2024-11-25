package com.grepp.quizy.game.infra.websocket

import com.grepp.quizy.game.domain.GameMessage
import com.grepp.quizy.game.domain.MessageType
import com.grepp.quizy.game.domain.RoomPayload
import com.grepp.quizy.game.domain.game.*
import com.grepp.quizy.game.infra.game.GameWebSocketMessageSender
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.springframework.messaging.simp.SimpMessagingTemplate
import java.security.Principal

class GameWebSocketMessageSenderTest :
    DescribeSpec({
        val messageTemplate: SimpMessagingTemplate = mockk()
        val messageSender =
            GameWebSocketMessageSender(messageTemplate)

        describe("GameWebSocketMessageSender") {
            context("메시지를 보내면") {
                val principal = mockk<Principal>()
                every { principal.name } returns "minhyeok"
                // Mock GameMessage 생성
                val mockGameSetting =
                    GameSetting(
                        GameSubject.JAVASCRIPT,
                        GameLevel.EASY,
                        10,
                    )
                val mockPlayers = Players(listOf())
                val mockInviteCode = InviteCode("ABC123")

                val mockRoomPayload =
                    RoomPayload(
                        setting = mockGameSetting,
                        status = GameStatus.WAITING,
                        players = mockPlayers,
                        inviteCode = mockInviteCode,
                    )

                val message =
                    GameMessage(
                        gameId = 1L,
                        type = MessageType.GAME_ROOM,
                        payload = mockRoomPayload,
                    )
                justRun {
                    messageTemplate.convertAndSendToUser(
                        any(),
                        any(),
                        any(),
                    )
                }
                messageSender.send(principal, message)
                it("메시지가 사용자에게 전달된다.") {
                    verify {
                        messageTemplate.convertAndSendToUser(
                            "minhyeok",
                            "/queue/quiz-grade",
                            message,
                        )
                    }
                }
            }
        }
    })
