package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.game.*
import com.grepp.quizy.game.domain.game.GameType.PRIVATE
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GameSettingManagerTest() : DescribeSpec({

    val gameRepository = FakeGameRepository()
    val gameSettingManager = GameSettingManager(gameRepository)

    afterTest {
        gameRepository.clear()
    }

    describe("GameSettingManager") {
        context("게임 설정 변경할 때") {
            context("게임 주제를 변경하면") {
                it("게임 주제가 변경된다.") {
                    val game = generateGameFixture(gameRepository)

                    val updatedGame = gameSettingManager.updateSubject(game, GameSubject.JAVASCRIPT, 1)

                    updatedGame.setting.subject shouldBe GameSubject.JAVASCRIPT
                }
            }

            context("게임 레벨을 변경하면") {
                it("게임 레벨이 변경된다.") {
                    val game = generateGameFixture(gameRepository)

                    val updatedGame = gameSettingManager.updateLevel(game, GameLevel.HARD, 1)

                    updatedGame.setting.level shouldBe GameLevel.HARD
                }
            }

            context("퀴즈 문항 개수를 변경하면") {
                it("퀴즈 문항 개수가 변경된다.") {
                    val game = generateGameFixture(gameRepository)

                    val updatedGame = gameSettingManager.updateQuizCount(game, 20, 1)

                    updatedGame.setting.quizCount shouldBe 20
                }
            }

        }
    }

})

private fun generateGameFixture(gameRepository: FakeGameRepository): Game {
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
                )
            )
        ),
        inviteCode = InviteCode("ABC123")
    )
    gameRepository.save(game)
    return game
}