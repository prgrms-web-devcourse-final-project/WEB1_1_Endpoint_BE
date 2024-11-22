package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.GameType.PRIVATE
import com.grepp.quizy.game.domain.exception.GameException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GameReaderTest() : DescribeSpec({

    val gameRepository = FakeGameRepository()
    val gameReader = GameReader(gameRepository)

    beforeTest {
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
                    Player(
                        id = 1,
                        PlayerRole.HOST
                    ),
                    Player(
                        id = 2,
                        PlayerRole.GUEST
                    )
                )
            ),
            inviteCode = InviteCode("ABC123")
        )
        gameRepository.save(game)
    }

    afterTest {
        gameRepository.clear()
    }

    describe("GameReader") {
        context("id로 게임을 조회 할 때") {
            context("게임이 존재하면") {
                it("게임을 반환한다.") {
                    val foundGame = gameReader.read(1)

                    foundGame.id shouldBe foundGame.id
                    foundGame.setting.subject shouldBe GameSubject.SPRING
                    foundGame.setting.level shouldBe GameLevel.EASY
                    foundGame.setting.quizCount shouldBe 10
                    foundGame.status shouldBe GameStatus.WAITING
                    foundGame.players.players.size shouldBe 2
                    foundGame.players.players[0].id shouldBe 1
                    foundGame.players.players[0].role shouldBe PlayerRole.HOST
                    foundGame.players.players[1].id shouldBe 2
                    foundGame.players.players[1].role shouldBe PlayerRole.GUEST
                    foundGame.inviteCode!!.value shouldBe "ABC123"
                }
            }
            context("게임이 존재하지 않으면") {
                it("예외를 발생시킨다.") {
                    shouldThrow<GameException.GameNotFoundException> { gameReader.read(123) }
                }
            }
        }
        context("초대 코드로 게임을 조회 할 때") {
            context("게임이 존재하면") {
                it("게임을 반환한다.") {
                    val foundGame = gameReader.readByInviteCode("ABC123")

                    foundGame.id shouldBe foundGame.id
                    foundGame.setting.subject shouldBe GameSubject.SPRING
                    foundGame.setting.level shouldBe GameLevel.EASY
                    foundGame.setting.quizCount shouldBe 10
                    foundGame.status shouldBe GameStatus.WAITING
                    foundGame.players.players.size shouldBe 2
                    foundGame.players.players[0].id shouldBe 1
                    foundGame.players.players[0].role shouldBe PlayerRole.HOST
                    foundGame.players.players[1].id shouldBe 2
                    foundGame.players.players[1].role shouldBe PlayerRole.GUEST
                    foundGame.inviteCode!!.value shouldBe "ABC123"
                }
            }
            context("게임이 존재하지 않으면") {
                it("예외를 발생시킨다.") {
                    shouldThrow<GameException.GameNotFoundException> { gameReader.readByInviteCode("ZXC123") }
                }
            }
        }
    }

})