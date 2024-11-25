package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.game.*
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GameAppenderTest(
) : DescribeSpec({
    val gameRepository = FakeGameRepository()

    val idGenerator = FakeIdGenerator()

    val gameAppender = GameAppender(gameRepository, idGenerator)

    describe("GameAppender") {
        context("게임을 추가하면") {
            it("게임이 추가된다.") {

                val appendedGame = gameAppender.append(
                    1L,
                    GameSubject.SPRING,
                    GameLevel.EASY,
                    10
                )

                appendedGame.id shouldBe appendedGame.id
                appendedGame.setting.subject shouldBe GameSubject.SPRING
                appendedGame.setting.level shouldBe GameLevel.EASY
                appendedGame.setting.quizCount shouldBe 10
                appendedGame.status shouldBe GameStatus.WAITING
                appendedGame.players.players.size shouldBe 1
                appendedGame.players.players[0].id shouldBe 1L
                appendedGame.players.players[0].role shouldBe PlayerRole.HOST
                appendedGame.inviteCode!!.value.length shouldBe 6
            }
        }
        context("랜덤 게임을 추가하면") {
            it("랜덤 게임이 생성된다.") {
                val randomGame = gameAppender.appendRandomGame(
                    listOf(
                        1L,
                        2L,
                        3L,
                        4L,
                        5L
                    ), GameSubject.SPRING
                )

                randomGame.id shouldBe randomGame.id
                randomGame.setting.subject shouldBe GameSubject.SPRING
                randomGame.setting.level shouldBe GameLevel.RANDOM
                randomGame.setting.quizCount shouldBe 10
                randomGame.status shouldBe GameStatus.WAITING
                randomGame.players.players.size shouldBe 5
            }
        }
    }
}) {

}