package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.game.*
import com.grepp.quizy.game.domain.user.User
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GameAppenderTest(
) : DescribeSpec({
    val gameRepository = FakeGameRepository()
    val idGenerator = FakeIdGenerator()
    val gameAppender = GameAppender(gameRepository, idGenerator)


    afterTest {
        gameRepository.clear()
        idGenerator.reset()
    }

    describe("GameAppender") {
        context("게임을 추가하면") {
            it("게임이 추가된다.") {

                val appendedGame = gameAppender.append(
                    user1,
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
                appendedGame.players.players[0].user.id shouldBe 1L
                appendedGame.players.players[0].user.name shouldBe "프로게이머"
                appendedGame.players.players[0].user.imgPath shouldBe "imgPath"
                appendedGame.players.players[0].user.rating shouldBe 1500
                appendedGame.players.players[0].role shouldBe PlayerRole.HOST
                appendedGame.inviteCode!!.value.length shouldBe 6
            }
        }
        context("랜덤 게임을 추가하면") {
            it("랜덤 게임이 생성된다.") {
                val randomGame = gameAppender.appendRandomGame(
                    listOf(
                        user1, user2, user3, user4, user5
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
    companion object {
        val user1 = User(1L, "프로게이머", "imgPath")
        val user2 = User(2L, "게임좋아", "imgPath123")
        val user3 = User(3L, "퀴즈왕", "img456")
        val user4 = User(4L, "퀴즈신", "imgPath")
        val user5 = User(5L, "퀴즈짱", "imgPath")
    }

}