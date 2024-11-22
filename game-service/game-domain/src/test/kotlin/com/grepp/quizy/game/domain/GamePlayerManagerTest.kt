package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.GameType.PRIVATE
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GamePlayerManagerTest() : DescribeSpec({

    val gameRepository = FakeGameRepository()
    val gamePlayerManager = GamePlayerManager(gameRepository)

    beforeTest {

    }

    afterTest {
        gameRepository.clear()
    }

    describe("GamePlayerManager") {
        context("참여 요청을 하면") {
            it("게임에 참여한다.") {
                val game = generateGameFixture(gameRepository)

                val joinedGame = gamePlayerManager.join(game, 3)

                joinedGame.players.players.size shouldBe 3
                joinedGame.players.players[0].id shouldBe 1
                joinedGame.players.players[0].role shouldBe PlayerRole.HOST
                joinedGame.players.players[1].id shouldBe 2
                joinedGame.players.players[1].role shouldBe PlayerRole.GUEST
                joinedGame.players.players[2].id shouldBe 3
                joinedGame.players.players[2].role shouldBe PlayerRole.GUEST
            }
        }
        context("게임에 참여한 플레이어가 나가면") {
            it("게임에서 나간다.") {
                val game = generateGameFixture(gameRepository)

                val leftGame = gamePlayerManager.quit(game, 2)

                leftGame.players.players.size shouldBe 1
                leftGame.players.players[0].id shouldBe 1
                leftGame.players.players[0].role shouldBe PlayerRole.HOST
            }
        }
        context("게임에 참여한 플레이어를 내보내면") {
            it("게임에서 내보낸다.") {
                val game = generateGameFixture(gameRepository)

                val kickedGame = gamePlayerManager.kick(game, 1, 2)

                kickedGame.players.players.size shouldBe 1
                kickedGame.players.players[0].id shouldBe 1
                kickedGame.players.players[0].role shouldBe PlayerRole.HOST
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
    return game
}