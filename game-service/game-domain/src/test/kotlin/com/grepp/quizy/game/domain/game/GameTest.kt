package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.exception.GameException
import com.grepp.quizy.game.domain.user.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GameTest() : DescribeSpec({

    describe("게임에서") {
        context("게임을 생성하면") {
            it("생성한 유저가 게임의 호스트로 저장한다.") {
                val game = Game.create(1L, GameSubject.JAVASCRIPT, 10, GameLevel.EASY, user1)

                game.players shouldBe Players(listOf(Player(user1, PlayerRole.HOST)))
            }
            it("게임의 기본 정보를 저장한다.") {
                val game = Game.create(1L, GameSubject.JAVASCRIPT, 10, GameLevel.EASY, user1)

                game.id shouldBe 1L
                game.setting.subject shouldBe GameSubject.JAVASCRIPT
                game.setting.level shouldBe GameLevel.EASY
                game.setting.quizCount shouldBe 10
            }
            it("게임의 상태는 대기 중이다.") {
                val game = Game.create(1L, GameSubject.JAVASCRIPT, 10, GameLevel.EASY, user1)

                game.status shouldBe GameStatus.WAITING
            }
            it("게임의 초대 코드는 6자리 문자열이다.") {
                val game = Game.create(1L, GameSubject.JAVASCRIPT, 10, GameLevel.EASY, user1)

                game.inviteCode!!.value.length shouldBe 6
            }
        }

        context("게임에 참가하면") {
            it("참가한 유저를 게임에 추가한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(user1, PlayerRole.HOST), Player(user2, PlayerRole.GUEST))),
                    inviteCode = InviteCode("ABC123")
                )

                game.join(user3)

                game.players shouldBe Players(listOf(Player(user1, PlayerRole.HOST), Player(user2), Player(user3)))
            }
            it("참가한 유저의 역할은 게스트이다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(user1, PlayerRole.HOST), Player(user2, PlayerRole.GUEST))),
                    InviteCode("ABC123")
                )

                game.join(user3)

                val newPlayer = game.players.players.last()
                newPlayer.user.id shouldBe 3
                newPlayer.role shouldBe PlayerRole.GUEST
            }
        }

        context("게임에서 게스트가 나가면") {
            it("게임에서 나간 유저를 게임에서 제거한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(user1, PlayerRole.HOST), Player(user2, PlayerRole.GUEST))),
                    InviteCode("ABC123")
                )

                game.quit(user2)

                game.players shouldBe Players(listOf(Player(user1, PlayerRole.HOST)))
            }
        }
        context("게임에서 방장이 나가면") {
            it("방장의 권한을 위임한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(user1, PlayerRole.HOST), Player(user2, PlayerRole.GUEST))),
                    InviteCode("ABC123")
                )

                game.quit(user1)

                val remainPlayer = game.players.players.first()
                remainPlayer.user.id shouldBe 2
                remainPlayer.role shouldBe PlayerRole.HOST
            }
        }

        context("게임에서 사용자가 모두 나가면") {
            it("게임을 삭제한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(user1, PlayerRole.HOST))),
                    InviteCode("ABC123")
                )

                game.quit(user1)

                game.status shouldBe GameStatus.DELETED
            }
        }

        context("방장이 사용자를 강퇴하면") {
            it("강퇴된 사용자를 게임에서 제거한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(user1, PlayerRole.HOST), Player(user2, PlayerRole.GUEST))),
                    InviteCode("ABC123")
                )

                game.kick(user1, user2)

                game.players shouldBe Players(listOf(Player(user1)))
            }
        }

        context("사용자가 강퇴하면") {
            it("예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(user1, PlayerRole.HOST), Player(user2, PlayerRole.GUEST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameHostPermissionException> {
                    game.kick(user2, user1)
                }

            }
        }

        context("게임의 주제를 변경하면") {
            it("원하는 주제로 변경한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(user1, PlayerRole.HOST))),
                    InviteCode("ABC123")
                )

                game.updateSubject(user1, GameSubject.SPRING)

                game.setting.subject shouldBe GameSubject.SPRING
            }
        }

        context("게임의 난이도를 변경하면") {
            it("원하는 난이도로 변경한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(user1, PlayerRole.HOST))),
                    InviteCode("ABC123")
                )

                game.updateLevel(user1, GameLevel.HARD)

                game.setting.level shouldBe GameLevel.HARD
            }
        }

        context("게임의 퀴즈 수를 변경하면") {
            it("원하는 수로 변경한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(user1, PlayerRole.HOST))),
                    InviteCode("ABC123")
                )

                game.updateQuizCount(user1, 20)

                game.setting.quizCount shouldBe 20
            }
        }

        context("게임이 대기 상태가 아니면") {
            it("게임에 참가하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.PLAYING,
                    Players(listOf(Player(user1, PlayerRole.HOST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.join(user2)
                }
            }

            it("강퇴하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.PLAYING,
                    Players(listOf(Player(user1, PlayerRole.HOST), Player(user2, PlayerRole.GUEST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.kick(user1, user2)
                }
            }

            it("주제를 변경하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.PLAYING,
                    Players(listOf(Player(user1, PlayerRole.HOST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.updateSubject(user1, GameSubject.SPRING)
                }
            }
            it("난이도를 변경하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.FINISHED,
                    Players(listOf(Player(user1, PlayerRole.HOST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.updateLevel(user1, GameLevel.HARD)
                }
            }
            it("퀴즈 수를 변경하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.DELETED,
                    Players(listOf(Player(user1, PlayerRole.HOST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.updateQuizCount(user1, 20)
                }
            }
        }

        context("호스트가 아니면") {
            it("주제를 변경하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(user1, PlayerRole.HOST), Player(user2, PlayerRole.GUEST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameHostPermissionException> {
                    game.updateSubject(user2, GameSubject.SPRING)
                }
            }
            it("난이도를 변경하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(user1, PlayerRole.HOST), Player(user2, PlayerRole.GUEST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameHostPermissionException> {
                    game.updateLevel(user2, GameLevel.HARD)
                }
            }
            it("퀴즈 수를 변경하면 예외가 발생한다.") {
                val game = Game(
                    1L,
                    type = GameType.PRIVATE,
                    GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                    GameStatus.WAITING,
                    Players(listOf(Player(user1, PlayerRole.HOST), Player(user2, PlayerRole.GUEST))),
                    InviteCode("ABC123")
                )

                shouldThrow<GameException.GameHostPermissionException> {
                    game.updateQuizCount(user2, 20)
                }
            }
        }
        context("모든 플레이어가 준비되었는지 확인하면") {
            context("대기 상태인 플레이어가 없으면") {
                it("true를 반환한다.") {
                    val game = Game(
                        1L,
                        type = GameType.PRIVATE,
                        GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                        GameStatus.WAITING,
                        Players(
                            listOf(
                                Player(user1, PlayerRole.HOST, PlayerStatus.JOINED),
                                Player(user2, PlayerRole.GUEST, PlayerStatus.JOINED),
                                Player(user3, PlayerRole.GUEST, PlayerStatus.JOINED),
                                Player(user4, PlayerRole.GUEST, PlayerStatus.JOINED),
                                Player(user5, PlayerRole.GUEST, PlayerStatus.JOINED)
                            )
                        ),
                        InviteCode("ABC123")
                    )

                    game.isReady() shouldBe true
                }
            }
            context("대기중인 사용자가 존재하면") {
                it("false를 반환한다.") {
                    val game = Game(
                        1L,
                        type = GameType.PRIVATE,
                        GameSetting(GameSubject.JAVASCRIPT, GameLevel.EASY, 10),
                        GameStatus.WAITING,
                        Players(
                            listOf(
                                Player(user1, PlayerRole.HOST, PlayerStatus.JOINED),
                                Player(user2, PlayerRole.GUEST, PlayerStatus.JOINED),
                                Player(user3, PlayerRole.GUEST, PlayerStatus.JOINED),
                                Player(user4, PlayerRole.GUEST, PlayerStatus.WAITING),
                                Player(user5, PlayerRole.GUEST, PlayerStatus.JOINED)
                            )
                        ),
                        InviteCode("ABC123")
                    )

                    game.isReady() shouldBe false
                }
            }
        }
        context("랜덤 게임을 생성하면") {
            it("랜덤 게임을 생성한다.") {
                val game = Game.random(1L, GameSubject.JAVASCRIPT, listOf(user1, user2, user3, user4, user5))

                game.type shouldBe GameType.RANDOM
                game.setting.subject shouldBe GameSubject.JAVASCRIPT
                game.players shouldBe Players(
                    listOf(
                        Player(user1),
                        Player(user2),
                        Player(user3),
                        Player(user4),
                        Player(user5)
                    )
                )
            }
            it("랜덤 게임은 초대 코드가 없다.") {
                val game = Game.random(1L, GameSubject.JAVASCRIPT, listOf(user1, user2, user3, user4, user5))

                game.inviteCode shouldBe null
            }
            it("랜덤 게임은 초기 설정이 있다.") {
                val game = Game.random(1L, GameSubject.JAVASCRIPT, listOf(user1, user2, user3, user4, user5))

                game.setting.level shouldBe GameLevel.RANDOM
            }
            it("랜덤 게임의 사용자는 대기 상태이다.") {
                val game = Game.random(1L, GameSubject.JAVASCRIPT, listOf(user1, user2, user3, user4, user5))

                game.players.players[0].status shouldBe PlayerStatus.WAITING
                game.players.players[1].status shouldBe PlayerStatus.WAITING
                game.players.players[2].status shouldBe PlayerStatus.WAITING
                game.players.players[3].status shouldBe PlayerStatus.WAITING
                game.players.players[4].status shouldBe PlayerStatus.WAITING
            }
            context("일정 인원이 아니라면") {
                it("게임을 생성할 수 없다.") {
                    shouldThrow<GameException.GameMisMatchNumberOfPlayersException> {
                        Game.random(1L, GameSubject.JAVASCRIPT, listOf(user1))
                    }
                }
            }
        }

    }
    describe("랜덤 게임에서") {
        context("게임에 참가하면") {
            it("참가상태로 변경한다.") {
                val game = Game.random(1L, GameSubject.JAVASCRIPT, listOf(user1, user2, user3, user4, user5))

                game.joinRandomGame(user1)

                game.players.players[0].status shouldBe PlayerStatus.JOINED
                game.players.players[1].status shouldBe PlayerStatus.WAITING
                game.players.players[2].status shouldBe PlayerStatus.WAITING
                game.players.players[3].status shouldBe PlayerStatus.WAITING
                game.players.players[4].status shouldBe PlayerStatus.WAITING
            }
        }
    }

}) {
    companion object {
        val user1 = User(1, "프로게이머", "imgPath")
        val user2 = User(2, "게임좋아", "imgPath123")
        val user3 = User(3, "게임신", "imgPath23")
        val user4 = User(4, "게임왕", "imgPath23")
        val user5 = User(5, "게임짱", "imgPath23")
    }
}