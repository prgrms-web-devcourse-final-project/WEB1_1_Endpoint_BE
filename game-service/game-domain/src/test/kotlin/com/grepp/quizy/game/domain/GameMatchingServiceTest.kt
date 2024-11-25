package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.game.*
import com.grepp.quizy.game.domain.game.GameType.RANDOM
import com.grepp.quizy.game.domain.game.PlayerRole.GUEST
import com.grepp.quizy.game.domain.game.PlayerRole.HOST
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GameMatchingServiceTest() : DescribeSpec({
    val gameRepository = FakeGameRepository()
    val idGenerator = FakeIdGenerator()

    val gameAppender = GameAppender(gameRepository, idGenerator)
    val gameReader = GameReader(gameRepository)
    val messagePublisher = FakeGameMessagePublisher()
    val eventPublisher = FakeGameApplicationEventPublisher()

    val matchingService = GameMatchingService(gameAppender, gameReader, messagePublisher, eventPublisher)

    afterTest {
        gameRepository.clear()
        idGenerator.reset()
        eventPublisher.clear()
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
    describe("join 메서드는") {
        context("게임이 ready 상태가 되면") {
            it("GameStartEvent를 발행한다") {
                val game = Game(
                    1L,
                    type = RANDOM,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(
                        listOf(
                            Player(1, HOST, PlayerStatus.JOINED),
                            Player(2, GUEST, PlayerStatus.JOINED),
                            Player(3, GUEST, PlayerStatus.JOINED),
                            Player(4, GUEST, PlayerStatus.WAITING),
                            Player(5, GUEST, PlayerStatus.JOINED)
                        )
                    ),
                    InviteCode("ABC123")
                )
                val savedGame = gameRepository.save(game)

                matchingService.join(4L, savedGame.id)

                val events = eventPublisher.getEvents()
                events.size shouldBe 1
                events.all { event -> event is GameStartEvent } shouldBe true
                val event = events.first() as GameStartEvent
                event.game.id shouldBe savedGame.id
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