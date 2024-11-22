package com.grepp.quizy.game.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GameMatchingServiceTest() : DescribeSpec({
    val gameRepository = FakeGameRepository()
    val idGenerator = FakeIdGenerator()

    val gameAppender = GameAppender(gameRepository, idGenerator)
    val gameReader = GameReader(gameRepository)
    val messagePublisher = FakeGameMessagePublisher()

    val matchingService = GameMatchingService(gameAppender, gameReader, messagePublisher)

    afterTest {
        gameRepository.clear()
        idGenerator.reset()
    }

    describe("GameMatchingService") {
        context("게임을 생성하면") {
            it("게임이 생성된다.") {
                val game = matchingService.create(
                    listOf(1L, 2L, 3L, 4L, 5L),
                    GameSubject.SPRING
                )

                game.id shouldBe game.id
                game.setting.subject shouldBe GameSubject.SPRING
                game.setting.level shouldBe GameLevel.RANDOM
                game.setting.quizCount shouldBe 10
                game.status shouldBe GameStatus.WAITING
                game.players.players.size shouldBe 5

            }
        }
        context("게임에 참여하면") {
            it("참여를 확인한다") {
                val randomGame = generateRandomGameFixture(gameRepository)

                matchingService.join(1L, randomGame.id)

                messagePublisher.getMessages().size shouldBe 1
                val gameMessage = messagePublisher.getMessages().first()
                gameMessage.gameId shouldBe randomGame.id
                gameMessage.type shouldBe MessageType.GAME_ROOM

                val roomPayload = gameMessage.payload as RoomPayload
                roomPayload.setting.subject shouldBe GameSubject.SPRING
                roomPayload.setting.level shouldBe GameLevel.RANDOM
                roomPayload.setting.quizCount shouldBe 10
                roomPayload.status shouldBe GameStatus.WAITING

            }
        }
    }
})

private fun generateRandomGameFixture(
    gameRepository: FakeGameRepository
) = gameRepository.save(
    Game.random(
        id = 1,
        subject = GameSubject.SPRING,
        userIds = listOf(1L, 2L, 3L, 4L, 5L)
    )
)