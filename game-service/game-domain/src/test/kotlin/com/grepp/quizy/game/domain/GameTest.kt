package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.PlayerRole.*
import com.grepp.quizy.game.domain.exception.GameException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GameTest() : DescribeSpec({

    describe("게임에서") {
        context("게임을 생성하면") {
            it("생성한 유저가 게임의 호스트로 저장한다.") {
                val game = Game.create(1L, GameSubject.JAVASCRIPT, 10, GameLevel.EASY, 1)

                game.players shouldBe Players(listOf(Player(1, HOST)))
            }
            it("게임의 기본 정보를 저장한다.") {
                val game = Game.create(1L, GameSubject.JAVASCRIPT, 10, GameLevel.EASY, 1)

                game.id shouldBe 1L
                game.setting.subject shouldBe GameSubject.JAVASCRIPT
                game.setting.level shouldBe GameLevel.EASY
                game.setting.quizCount shouldBe 10
            }
            it("게임의 상태는 대기 중이다.") {
                val game = Game.create(1L, GameSubject.JAVASCRIPT, 10, GameLevel.EASY, 1)

                game.status shouldBe GameStatus.WAITING
            }
            it("게임의 초대 코드는 6자리 문자열이다.") {
                val game = Game.create(1L, GameSubject.JAVASCRIPT, 10, GameLevel.EASY, 1)

                game.inviteCode.value.length shouldBe 6
            }
        }

        context("게임에 참가하면") {
            it("참가한 유저를 게임에 추가한다.") {
                val game = Game(
                    1L,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST)))
                )

                game.join(3)

                game.players shouldBe Players(listOf(Player(1, HOST), Player(2), Player(3)))
            }
            it("참가한 유저의 역할은 게스트이다.") {
                val game = Game(
                    1L,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST)))
                )

                game.join(3)

                val newPlayer = game.players.players.last()
                newPlayer.id shouldBe 3
                newPlayer.role shouldBe GUEST
            }
        }

        context("게임에서 게스트가 나가면") {
            it("게임에서 나간 유저를 게임에서 제거한다.") {
                val game = Game(
                    1L,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST)))
                )

                game.quit(2)

                game.players shouldBe Players(listOf(Player(1, HOST)))
            }
        }
        context("게임에서 방장이 나가면") {
            it("방장의 권한을 위임한다.") {
                val game = Game(
                    1L,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST)))
                )

                game.quit(1)

                val remainPlayer = game.players.players.first()
                remainPlayer.id shouldBe 2
                remainPlayer.role shouldBe HOST
            }
        }

        context("게임에서 사용자가 모두 나가면") {
            it("게임을 삭제한다.") {
                val game = Game(
                    1L,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST)))
                )

                game.quit(1)

                game.status shouldBe GameStatus.DELETED
            }
        }

        context("방장이 사용자를 강퇴하면") {
            it("강퇴된 사용자를 게임에서 제거한다.") {
                val game = Game(
                    1L,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST)))
                )

                game.kick(1, 2)

                game.players shouldBe Players(listOf(Player(1)))
            }
        }

        context("사용자가 강퇴하면") {
            it("예외가 발생한다.") {
                val game = Game(
                    1L,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST)))
                )

                shouldThrow<GameException.GameHostPermissionException> {
                    game.kick(2, 1)
                }

            }
        }

    }

}) {

}