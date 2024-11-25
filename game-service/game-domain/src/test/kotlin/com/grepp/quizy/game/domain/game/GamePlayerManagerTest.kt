package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.game.GamePlayerManagerTest.Companion.guestUser1
import com.grepp.quizy.game.domain.game.GamePlayerManagerTest.Companion.hostUser
import com.grepp.quizy.game.domain.game.GameType.PRIVATE
import com.grepp.quizy.game.domain.user.User
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

                val joinedGame = gamePlayerManager.join(game, guestUser2)

                joinedGame.players.players.size shouldBe 3
                joinedGame.players.players[0].user.id shouldBe 1
                joinedGame.players.players[0].role shouldBe PlayerRole.HOST
                joinedGame.players.players[1].user.id shouldBe 2
                joinedGame.players.players[1].role shouldBe PlayerRole.GUEST
                joinedGame.players.players[2].user.id shouldBe 3
                joinedGame.players.players[2].role shouldBe PlayerRole.GUEST
            }
        }
        context("게임에 참여한 플레이어가 나가면") {
            it("게임에서 나간다.") {
                val game = generateGameFixture(gameRepository)

                val leftGame = gamePlayerManager.quit(game, guestUser1)

                leftGame.players.players.size shouldBe 1
                leftGame.players.players[0].user.id shouldBe 1
                leftGame.players.players[0].role shouldBe PlayerRole.HOST
            }
        }
        context("게임에 참여한 플레이어를 내보내면") {
            it("게임에서 내보낸다.") {
                val game = generateGameFixture(gameRepository)

                val kickedGame = gamePlayerManager.kick(game, hostUser, guestUser1)

                kickedGame.players.players.size shouldBe 1
                kickedGame.players.players[0].user.id shouldBe 1
                kickedGame.players.players[0].role shouldBe PlayerRole.HOST
            }
        }
    }
}) {
    companion object {
        val hostUser = User(1, "프로게이머", "imgPath")
        val guestUser1 = User(2, "게임좋아", "imgPath123")
        val guestUser2 = User(3, "게임신", "imgPath23")
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