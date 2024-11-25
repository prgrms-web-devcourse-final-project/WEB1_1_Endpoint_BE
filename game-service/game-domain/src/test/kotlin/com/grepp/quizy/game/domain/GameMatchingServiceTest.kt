package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.GameMatchingServiceTest.Companion.user1
import com.grepp.quizy.game.domain.GameMatchingServiceTest.Companion.user2
import com.grepp.quizy.game.domain.GameMatchingServiceTest.Companion.user3
import com.grepp.quizy.game.domain.GameMatchingServiceTest.Companion.user4
import com.grepp.quizy.game.domain.GameMatchingServiceTest.Companion.user5
import com.grepp.quizy.game.domain.game.*
import com.grepp.quizy.game.domain.game.GameType.RANDOM
import com.grepp.quizy.game.domain.game.PlayerRole.GUEST
import com.grepp.quizy.game.domain.game.PlayerRole.HOST
import com.grepp.quizy.game.domain.user.User
import com.grepp.quizy.game.domain.user.UserReader
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GameMatchingServiceTest() : DescribeSpec({
    val gameRepository = FakeGameRepository()
    val idGenerator = FakeIdGenerator()

    val gameAppender = GameAppender(gameRepository, idGenerator)
    val gameReader = GameReader(gameRepository)
    val messagePublisher = FakeGameMessagePublisher()
    val eventPublisher = FakeGameApplicationEventPublisher()
    val userRepository = FakeUserRepository()
    val userReader = UserReader(userRepository)


    val matchingService = GameMatchingService(gameAppender, gameReader, userReader, messagePublisher, eventPublisher)

    beforeTest {
        userRepository.saveAll(listOf(user1, user2, user3, user4, user5))
    }

    afterTest {
        gameRepository.clear()
        idGenerator.reset()
        eventPublisher.clear()
        userRepository.clear()
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
                            Player(
                                user1,
                                HOST,
                                PlayerStatus.JOINED
                            ),
                            Player(
                                user2,
                                GUEST,
                                PlayerStatus.JOINED
                            ),
                            Player(
                                user3,
                                GUEST,
                                PlayerStatus.JOINED
                            ),
                            Player(
                                user4,
                                GUEST,
                                PlayerStatus.WAITING
                            ),
                            Player(
                                user5,
                                GUEST,
                                PlayerStatus.JOINED
                            )
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
}) {
    companion object {
        val user1 = User(1L, "프로게이머", "imgPath")
        val user2 = User(2L, "게임좋아", "imgPath123")
        val user3 = User(3L, "퀴즈왕", "img456")
        val user4 = User(4L, "퀴즈신", "imgPath")
        val user5 = User(5L, "퀴즈짱", "imgPath")
    }
}

private fun generateRandomGameFixture(
    gameRepository: FakeGameRepository
) = gameRepository.save(
    Game.random(
        id = 1,
        subject = GameSubject.SPRING,
        users = listOf(
            user1, user2, user3, user4, user5
        )
    )
)