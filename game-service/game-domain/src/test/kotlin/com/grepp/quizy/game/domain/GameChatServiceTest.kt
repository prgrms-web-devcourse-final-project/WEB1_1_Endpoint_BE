package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.game.GameChatService
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GameChatServiceTest() : DescribeSpec({

    val messagePublisher = FakeGameMessagePublisher()
    val gameChatService = GameChatService(messagePublisher)

    describe("GameChatService") {
        context("채팅을 보내면") {
            it("메시지를 게임 채팅방에 전달한다.") {
                val userId = 1L
                val gameId = 1L
                val message = "😀"

                gameChatService.publishChat(userId, gameId, message)

                messagePublisher.getMessages().size shouldBe 1
                val publishedMessage = messagePublisher.getMessages().first()

                publishedMessage.gameId shouldBe 1L
                publishedMessage.type shouldBe MessageType.CHAT

                val chatPayload = publishedMessage.payload as ChatPayload
                chatPayload.userId shouldBe 1L
                chatPayload.message shouldBe "😀"

            }
        }
    }
})