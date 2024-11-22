package com.grepp.quizy.game.domain

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
                appendedGame.inviteCode.value.length shouldBe 6
            }
        }
    }
}) {

}