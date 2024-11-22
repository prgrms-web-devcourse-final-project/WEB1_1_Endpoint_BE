package com.grepp.quizy.game.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GameMatchingServiceTest() : DescribeSpec({
    val gameRepository = FakeGameRepository()
    val idGenerator = FakeIdGenerator()
    val gameAppender = GameAppender(gameRepository, idGenerator)
    val matchingService = GameMatchingService(gameAppender)

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
    }
}) {

}