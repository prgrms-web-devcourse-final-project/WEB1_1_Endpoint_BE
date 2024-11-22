package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.GameType.PRIVATE
import com.grepp.quizy.game.domain.PlayerRole.GUEST
import com.grepp.quizy.game.domain.PlayerRole.HOST
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

                game.inviteCode!!.value.length shouldBe 6
            }
        }

        context("게임에 참가하면") {
            it("참가한 유저를 게임에 추가한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST))),
                    inviteCode = InviteCode("ABC123")
                )

                game.join(3)

                game.players shouldBe Players(listOf(Player(1, HOST), Player(2), Player(3)))
            }
            it("참가한 유저의 역할은 게스트이다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST))),
                    InviteCode("ABC123")
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
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST))),
                    InviteCode("ABC123")
                )

                game.quit(2)

                game.players shouldBe Players(listOf(Player(1, HOST)))
            }
        }
        context("게임에서 방장이 나가면") {
            it("방장의 권한을 위임한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST))),
                    InviteCode("ABC123")
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
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST))),
                    InviteCode("ABC123")
                )

                game.quit(1)

                game.status shouldBe GameStatus.DELETED
            }
        }

        context("방장이 사용자를 강퇴하면") {
            it("강퇴된 사용자를 게임에서 제거한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST))),
                    InviteCode("ABC123")
                )

                game.kick(1, 2)

                game.players shouldBe Players(listOf(Player(1)))
            }
        }

        context("사용자가 강퇴하면") {
            it("예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameHostPermissionException> {
                    game.kick(2, 1)
                }

            }
        }

        context("게임의 주제를 변경하면") {
            it("원하는 주제로 변경한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST))),
                    InviteCode("ABC123")
                )

                game.updateSubject(1, GameSubject.SPRING)

                game.setting.subject shouldBe GameSubject.SPRING
            }
        }

        context("게임의 난이도를 변경하면") {
            it("원하는 난이도로 변경한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST))),
                    InviteCode("ABC123")
                )

                game.updateLevel(1, GameLevel.HARD)

                game.setting.level shouldBe GameLevel.HARD
            }
        }

        context("게임의 퀴즈 수를 변경하면") {
            it("원하는 수로 변경한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST))),
                    InviteCode("ABC123")
                )

                game.updateQuizCount(1, 20)

                game.setting.quizCount shouldBe 20
            }
        }

        context("게임이 대기 상태가 아니면") {
            it("게임에 참가하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.PLAYING,
                    Players(listOf(Player(1, HOST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.join(2)
                }
            }

            it("강퇴하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.PLAYING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.kick(1, 2)
                }
            }

            it("주제를 변경하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.PLAYING,
                    Players(listOf(Player(1, HOST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.updateSubject(1, GameSubject.SPRING)
                }
            }
            it("난이도를 변경하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.FINISHED,
                    Players(listOf(Player(1, HOST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.updateLevel(1, GameLevel.HARD)
                }
            }
            it("퀴즈 수를 변경하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.DELETED,
                    Players(listOf(Player(1, HOST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.updateQuizCount(1, 20)
                }
            }
        }

        context("호스트가 아니면") {
            it("주제를 변경하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameHostPermissionException> {
                    game.updateSubject(2, GameSubject.SPRING)
                }
            }
            it("난이도를 변경하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameHostPermissionException> {
                    game.updateLevel(2, GameLevel.HARD)
                }
            }
            it("퀴즈 수를 변경하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(1, HOST), Player(2, GUEST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameHostPermissionException> {
                    game.updateQuizCount(2, 20)
                }
            }
        }
        context("랜덤 게임을 생성하면") {
            it("랜덤 게임을 생성한다.") {
                val game = Game.random(1L, GameSubject.JAVASCRIPT, listOf(1, 2, 3, 4, 5))

                game.type shouldBe GameType.RANDOM
                game.setting.subject shouldBe GameSubject.JAVASCRIPT
                game.players shouldBe Players(listOf(Player(1), Player(2), Player(3), Player(4), Player(5)))
            }
            it("랜덤 게임은 초대 코드가 없다.") {
                val game = Game.random(1L, GameSubject.JAVASCRIPT, listOf(1, 2, 3, 4, 5))

                game.inviteCode shouldBe null
            }
            it("랜덤 게임은 초기 설정이 있다.") {
                val game = Game.random(1L, GameSubject.JAVASCRIPT, listOf(1, 2, 3, 4, 5))

                game.setting.level shouldBe GameLevel.RANDOM
            }
            context("일정 인원이 아니라면") {
                it("게임을 생성할 수 없다.") {
                    shouldThrow<GameException.GameMisMatchNumberOfPlayersException> {
                        Game.random(1L, GameSubject.JAVASCRIPT, listOf(1))
                    }
                }
            }
        }

    }

}) {

}