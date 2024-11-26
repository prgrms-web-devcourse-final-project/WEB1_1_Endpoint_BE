package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.MessageType
import com.grepp.quizy.game.domain.RoomPayload
import com.grepp.quizy.game.domain.game.GamePrivateServiceTest.Companion.guestUser1
import com.grepp.quizy.game.domain.game.GamePrivateServiceTest.Companion.hostUser
import com.grepp.quizy.game.domain.game.GameType.PRIVATE
import com.grepp.quizy.game.domain.user.FakeUserRepository
import com.grepp.quizy.game.domain.user.User
import com.grepp.quizy.game.domain.user.UserReader
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GamePrivateServiceTest() : DescribeSpec({
    val gameRepository = FakeGameRepository()
    val idGenerator = FakeIdGenerator()
    val gameMessagePublisher = FakeGameMessagePublisher()

    val appender = GameAppender(gameRepository, idGenerator)
    val reader = GameReader(gameRepository)
    val playerManager = GamePlayerManager(gameRepository)
    val settingManager = GameSettingManager(gameRepository)
    val userRepository = FakeUserRepository()
    val userReader = UserReader(userRepository)

    val gamePrivateService = GamePrivateService(
        appender,
        reader,
        playerManager,
        settingManager,
        userReader,
        gameMessagePublisher
    )

    beforeTest {
        userRepository.saveAll(listOf(hostUser, guestUser1, guestUser2, guestUser3, guestUser4))
    }

    afterTest {
        gameRepository.clear()
        idGenerator.reset()
        gameMessagePublisher.clear()
        userRepository.clear()
    }

    describe("GameService") {
        context("게임을 생성하면") {
            it("게임이 생성되고 저장된다.") {
                val createdGame = gamePrivateService.create(
                    1L,
                    GameSubject.JAVASCRIPT,
                    GameLevel.EASY,
                    10
                )

                createdGame.id shouldBe createdGame.id
                createdGame.setting.subject shouldBe GameSubject.JAVASCRIPT
                createdGame.setting.level shouldBe GameLevel.EASY
                createdGame.setting.quizCount shouldBe 10
                createdGame.status shouldBe GameStatus.WAITING
                createdGame.players.players.size shouldBe 1
                createdGame.players.players[0].user.id shouldBe 1L
                createdGame.players.players[0].role shouldBe PlayerRole.HOST
                createdGame.inviteCode!!.value.length shouldBe 6

            }
        }
        context("게임에 참가하면") {
            it("게임에 참가한 유저가 추가된다.") {
                val game = generateGameFixture(gameRepository)

                val joinedGame = gamePrivateService.join(3, "ABC123")

                // 게임 상태 검증
                joinedGame.players.players.size shouldBe 3
                joinedGame.players.players[2].user.id shouldBe 3L
                joinedGame.players.players[2].role shouldBe PlayerRole.GUEST

                // 메시지 검증
                gameMessagePublisher.getMessages().size shouldBe 1
                val publishedMessage = gameMessagePublisher.getMessages().first()
                publishedMessage.gameId shouldBe game.id
                publishedMessage.type shouldBe MessageType.GAME_ROOM

                // GamePayload 검증
                val payload = gameMessagePublisher.getMessages().first().payload as RoomPayload

                payload.setting.subject shouldBe GameSubject.SPRING
                payload.setting.level shouldBe GameLevel.EASY
                payload.setting.quizCount shouldBe 10
                payload.status shouldBe GameStatus.WAITING
                payload.players.players.size shouldBe 3
                payload.players.players[0].user.id shouldBe 1L
                payload.players.players[0].role shouldBe PlayerRole.HOST
                payload.players.players[1].user.id shouldBe 2L
                payload.players.players[1].role shouldBe PlayerRole.GUEST
                payload.players.players[2].user.id shouldBe 3L
                payload.players.players[2].role shouldBe PlayerRole.GUEST
                payload.inviteCode!!.value shouldBe "ABC123"

            }
        }
        context("게임에서 나가면") {
            it("게임에서 사용자가 제거된다.") {
                val game = generateGameFixture(gameRepository)

                gamePrivateService.quit(2, game.id)

                // 메시지 검증
                gameMessagePublisher.getMessages().size shouldBe 1
                val publishedMessage = gameMessagePublisher.getMessages().first()
                publishedMessage.gameId shouldBe game.id
                publishedMessage.type shouldBe MessageType.GAME_ROOM

                // GamePayload 검증
                val payload = gameMessagePublisher.getMessages().first().payload as RoomPayload

                payload.setting.subject shouldBe GameSubject.SPRING
                payload.setting.level shouldBe GameLevel.EASY
                payload.setting.quizCount shouldBe 10
                payload.status shouldBe GameStatus.WAITING
                payload.players.players.size shouldBe 1
                payload.players.players[0].user.id shouldBe 1L
                payload.players.players[0].role shouldBe PlayerRole.HOST
                payload.inviteCode!!.value shouldBe "ABC123"
            }
        }
        context("게임 주제를 변경하면") {
            it("게임 주제가 변경된다.") {
                val game = generateGameFixture(gameRepository)

                gamePrivateService.updateSubject(1, game.id, GameSubject.JAVASCRIPT)

                // 메시지 검증
                gameMessagePublisher.getMessages().size shouldBe 1
                val publishedMessage = gameMessagePublisher.getMessages().first()
                publishedMessage.gameId shouldBe game.id
                publishedMessage.type shouldBe MessageType.GAME_ROOM

                // GamePayload 검증
                val payload = gameMessagePublisher.getMessages().first().payload as RoomPayload

                payload.setting.subject shouldBe GameSubject.JAVASCRIPT
                payload.setting.level shouldBe GameLevel.EASY
                payload.setting.quizCount shouldBe 10
                payload.status shouldBe GameStatus.WAITING
                payload.players.players.size shouldBe 2
                payload.players.players[0].user.id shouldBe 1L
                payload.players.players[0].role shouldBe PlayerRole.HOST
                payload.players.players[1].user.id shouldBe 2L
                payload.players.players[1].role shouldBe PlayerRole.GUEST
                payload.inviteCode!!.value shouldBe "ABC123"
            }
        }
        context("게임 난이도를 변경하면") {
            it("게임 난이도가 변경된다.") {
                val game = generateGameFixture(gameRepository)

                gamePrivateService.updateLevel(1, game.id, GameLevel.HARD)

                // 메시지 검증
                gameMessagePublisher.getMessages().size shouldBe 1
                val publishedMessage = gameMessagePublisher.getMessages().first()
                publishedMessage.gameId shouldBe game.id
                publishedMessage.type shouldBe MessageType.GAME_ROOM

                // GamePayload 검증
                val payload = gameMessagePublisher.getMessages().first().payload as RoomPayload

                payload.setting.subject shouldBe GameSubject.SPRING
                payload.setting.level shouldBe GameLevel.HARD
                payload.setting.quizCount shouldBe 10
                payload.status shouldBe GameStatus.WAITING
                payload.players.players.size shouldBe 2
                payload.players.players[0].user.id shouldBe 1L
                payload.players.players[0].role shouldBe PlayerRole.HOST
                payload.players.players[1].user.id shouldBe 2L
                payload.players.players[1].role shouldBe PlayerRole.GUEST
                payload.inviteCode!!.value shouldBe "ABC123"
            }
        }
        context("게임 퀴즈 수를 변경하면") {
            it("게임 퀴즈 수가 변경된다.") {
                val game = generateGameFixture(gameRepository)

                val updatedGame = gamePrivateService.updateQuizCount(1, game.id, 20)

                // 메시지 검증
                gameMessagePublisher.getMessages().size shouldBe 1
                val publishedMessage = gameMessagePublisher.getMessages().first()
                publishedMessage.gameId shouldBe game.id
                publishedMessage.type shouldBe MessageType.GAME_ROOM

                // GamePayload 검증
                val payload = gameMessagePublisher.getMessages().first().payload as RoomPayload

                payload.setting.subject shouldBe GameSubject.SPRING
                payload.setting.level shouldBe GameLevel.EASY
                payload.setting.quizCount shouldBe 20
                payload.status shouldBe GameStatus.WAITING
                payload.players.players.size shouldBe 2
                payload.players.players[0].user.id shouldBe 1L
                payload.players.players[0].role shouldBe PlayerRole.HOST
                payload.players.players[1].user.id shouldBe 2L
                payload.players.players[1].role shouldBe PlayerRole.GUEST
                payload.inviteCode!!.value shouldBe "ABC123"
            }
        }
        context("사용자를 강퇴하면") {
            it("게임에서 사용자가 제거된다.") {
                val game = generateGameFixture(gameRepository)

                gamePrivateService.kickUser(1, game.id, 2)

                // 메시지 검증
                gameMessagePublisher.getMessages().size shouldBe 1
                val publishedMessage = gameMessagePublisher.getMessages().first()
                publishedMessage.gameId shouldBe game.id
                publishedMessage.type shouldBe MessageType.GAME_ROOM

                // GamePayload 검증
                val payload = gameMessagePublisher.getMessages().first().payload as RoomPayload

                payload.setting.subject shouldBe GameSubject.SPRING
                payload.setting.level shouldBe GameLevel.EASY
                payload.setting.quizCount shouldBe 10
                payload.status shouldBe GameStatus.WAITING
                payload.players.players.size shouldBe 1
                payload.players.players[0].user.id shouldBe 1L
                payload.players.players[0].role shouldBe PlayerRole.HOST
                payload.inviteCode!!.value shouldBe "ABC123"
            }
        }
    }

}) {
    companion object {
        val hostUser = User(1L, "프로게이머", "imgPath")
        val guestUser1 = User(2L, "게임좋아", "imgPath123")
        val guestUser2 = User(3L, "게임신", "imgPath23")
        val guestUser3 = User(4L, "퀴즈신", "imgPath")
        val guestUser4 = User(5L, "퀴즈짱", "imgPath")
    }
}

private fun generateGameFixture(gameRepository: FakeGameRepository): Game {
    val hostPlayer = Player(
        hostUser,
        PlayerRole.HOST
    )
    val guestPlayer1 = Player(
        guestUser1,
        PlayerRole.GUEST
    )
    val game = Game(
        type = PRIVATE,
        _setting = GameSetting(
            subject = GameSubject.SPRING,
            level = GameLevel.EASY,
            quizCount = 10
        ),
        _status = GameStatus.WAITING,
        _players = Players(
            listOf(
                hostPlayer,
                guestPlayer1
            )
        ),
        inviteCode = InviteCode("ABC123")
    )
    return gameRepository.save(game)
}