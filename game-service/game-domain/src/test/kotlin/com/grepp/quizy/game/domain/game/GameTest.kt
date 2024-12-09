package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.exception.GameException
import com.grepp.quizy.game.domain.user.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class GameTest : BehaviorSpec({

    given("게임 생성") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val user2 = User(2, "황민우", "https://cdn.example.com/v2/users/2/profile.webp")
        val user3 = User(3, "윤수빈", "https://cdn.example.com/v2/users/3/profile.webp")
        val user4 = User(4, "강지원", "https://cdn.example.com/v2/users/4/profile.webp")
        val user5 = User(5, "장원석", "https://cdn.example.com/v2/users/5/profile.webp")
        `when`("사설 게임을 생성할 때") {
            val game = Game.create(
                1,
                GameSubject.ALGORITHM,
                5,
                GameLevel.EASY,
                user
            )
            then("기본 정보가 포함된 사설 게임이 생성되어야 한다.") {
                game.id shouldBe 1
                game.setting.subject shouldBe GameSubject.ALGORITHM
                game.setting.quizCount shouldBe 5
                game.setting.level shouldBe GameLevel.EASY
                game.type shouldBe GameType.PRIVATE
                game.status shouldBe GameStatus.WAITING
            }
            then("초대 코드가 생성되어야 한다.") {
                game.inviteCode?.value?.length shouldBe 6
            }
            then("게임 생성자가 호스트로 추가되어야 한다.") {
                game.players.players[0].role shouldBe PlayerRole.HOST
            }
        }
        `when`("랜덤 게임을 생성 할 때") {
            val randomGame = Game.random(
                1,
                GameSubject.ALGORITHM,
                listOf(user, user2, user3, user4, user5)
            )
            then("랜덤 게임이 생성되어야 한다.") {
                randomGame.id shouldBe 1
                randomGame.setting.subject shouldBe GameSubject.ALGORITHM
                randomGame.type shouldBe GameType.RANDOM
            }
            then("랜덤 게임은 기본 퀴즈 갯수가 존재한다.") {
                randomGame.setting.quizCount shouldBe 5
            }
            then("랜덤 게임은 랜덤 레벨이 존재한다.") {
                randomGame.setting.level shouldBe GameLevel.RANDOM
            }
            then("랜덤 게임은 참가자가 추가되어야 한다.") {
                randomGame.players.players.size shouldBe 5
            }
            then("랜덤 게임은 초대코드가 없어야 한다.") {
                randomGame.inviteCode shouldBe null
            }
        }
        `when`("랜덤 게임을 생성할 때 일정 인원이 아니면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameMisMatchNumberOfPlayersException> {
                    Game.random(
                        1,
                        GameSubject.ALGORITHM,
                        listOf(user, user2)
                    )
                }
            }
        }
    }

    given("사설 게임 대기방 참가") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val user2 = User(2, "황민우", "https://cdn.example.com/v2/users/2/profile.webp")

        val game = Game.create(
            1,
            GameSubject.ALGORITHM,
            5,
            GameLevel.EASY,
            user
        )

        `when`("사설 게임에 참가할 때") {
            game.join(user2)
            then("게임에 참가자가 추가되어야 한다.") {
                game.players.players.size shouldBe 2
                game.players.players[1].role shouldBe PlayerRole.GUEST
                game.players.players[1].user.id shouldBe 2
                game.players.players[1].user.name shouldBe "황민우"
                game.players.players[1].user.imgPath shouldBe "https://cdn.example.com/v2/users/2/profile.webp"
            }
        }
        `when`("이미 사설 게임에 참가한 유저가 다시 참가하려고 할 때") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameAlreadyParticipatedException> {
                    game.join(user)
                }
            }
        }
    }
    given("이미 시작된 게임방") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val user2 = User(2, "황민우", "https://cdn.example.com/v2/users/2/profile.webp")
        val user3 = User(3, "윤수빈", "https://cdn.example.com/v2/users/3/profile.webp")

        val game = Game(
            1,
            GameType.PRIVATE,
            GameSetting(
                GameSubject.ALGORITHM,
                GameLevel.EASY,
                5
            ),
            GameStatus.PLAYING,
            Players(
                listOf(
                    Player(user, PlayerRole.HOST),
                    Player(user2, PlayerRole.GUEST),
                    Player(user3, PlayerRole.GUEST)
                )
            ),
            null
        )
        `when`("참가하려고 하면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.join(user)
                }
            }
        }
        `when`("강퇴하려고 하면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.kick(2, 3)
                }
            }
        }
        `when`("게임 주제를 바꾸려고 하면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.updateSubject(1, GameSubject.DATABASE)
                }
            }
        }
        `when`("게임 퀴즈 갯수를 변경하려고 하면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.updateQuizCount(1, 10)
                }
            }
        }
        `when`("게임 난이도를 바꾸려고 하면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameAlreadyStartedException> {
                    game.updateLevel(1, GameLevel.HARD)
                }
            }
        }
    }
    given("랜덤 매칭 게임 접속") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val user2 = User(2, "황민우", "https://cdn.example.com/v2/users/2/profile.webp")
        val user3 = User(3, "윤수빈", "https://cdn.example.com/v2/users/3/profile.webp")
        val user4 = User(4, "강지원", "https://cdn.example.com/v2/users/4/profile.webp")
        val user5 = User(5, "장원석", "https://cdn.example.com/v2/users/5/profile.webp")

        val randomGame = Game.random(
            1,
            GameSubject.ALGORITHM,
            listOf(user, user2, user3, user4, user5)
        )

        `when`("랜덤 매칭 게임에 참가 하면") {
            randomGame.joinRandomGame(1)
            then("플레이어가 준비상태가 된다.") {
                randomGame.players.players.size shouldBe 5
                randomGame.players.players[0].status shouldBe PlayerStatus.JOINED
            }
        }
    }
    given("게임 방에서") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val user2 = User(2, "황민우", "https://cdn.example.com/v2/users/2/profile.webp")
        val user3 = User(3, "윤수빈", "https://cdn.example.com/v2/users/3/profile.webp")

        val game = Game(
            1,
            GameType.PRIVATE,
            GameSetting(
                GameSubject.ALGORITHM,
                GameLevel.EASY,
                5
            ),
            GameStatus.WAITING,
            Players(
                listOf(
                    Player(user, PlayerRole.HOST),
                    Player(user2, PlayerRole.GUEST),
                    Player(user3, PlayerRole.GUEST)
                )
            ),
            null
        )

        `when`("사용자가 나가면") {
            game.quit(2)
            then("플레이어가 나가고 나간 플레이어는 제외된다.") {
                game.players.players.size shouldBe 2
                game.players.players[0].user.id shouldBe 1
                game.players.players[1].user.id shouldBe 3
            }
        }
        `when`("방장이 사용자를 강퇴하면") {
            game.kick(1, 3)
            then("사용자가 제외된다.") {
                game.players.players.size shouldBe 1
                game.players.players[0].user.id shouldBe 1
            }
        }

        `when`("게임을 시작하면") {
            game.start(1)
            then("게임이 시작된다.") {
                game.status shouldBe GameStatus.PLAYING
            }
        }

    }
    given("게임방에서 방장이 아닌 사용자가") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val user2 = User(2, "황민우", "https://cdn.example.com/v2/users/2/profile.webp")
        val user3 = User(3, "윤수빈", "https://cdn.example.com/v2/users/3/profile.webp")

        val game = Game(
            1,
            GameType.PRIVATE,
            GameSetting(
                GameSubject.ALGORITHM,
                GameLevel.EASY,
                5
            ),
            GameStatus.WAITING,
            Players(
                listOf(
                    Player(user, PlayerRole.HOST),
                    Player(user2, PlayerRole.GUEST),
                    Player(user3, PlayerRole.GUEST)
                )
            ),
            null
        )
        `when`("게임 주제를 변경하려고 하면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameHostPermissionException> {
                    game.updateSubject(2, GameSubject.DATABASE)
                }
            }
        }
        `when`("게임 퀴즈 갯수를 변경하려고 하면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameHostPermissionException> {
                    game.updateQuizCount(2, 10)
                }
            }
        }
        `when`("게임 난이도를 변경하려고 하면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameHostPermissionException> {
                    game.updateLevel(2, GameLevel.HARD)
                }
            }
        }
        `when`("방장이 아닌 사람이 게임을 시작하면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameHostPermissionException> {
                    game.start(2)
                }
            }
        }
        `when`("방장이 아닌 사용자가 강퇴요청을 하면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameHostPermissionException> {
                    game.kick(2, 3)
                }
            }
        }
    }

    given("혼자 있는 게임 방에서") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val game = Game.create(1, GameSubject.ALGORITHM, 5, GameLevel.EASY, user)
        `when`("사용자가 나가면") {
            game.quit(1)
            then("게임 방이 삭제된다.") {
                game.players.players.size shouldBe 0
                game.status shouldBe GameStatus.DELETED
            }
        }
    }

    given("사설방의 방장은") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val game = Game.create(1, GameSubject.ALGORITHM, 5, GameLevel.EASY, user)
        `when`("주제를 변경하면") {
            game.updateSubject(1, GameSubject.DATABASE)
            then("주제가 변경된다.") {
                game.setting.subject shouldBe GameSubject.DATABASE
            }
        }
        `when`("퀴즈 갯수를 변경하면") {
            game.updateQuizCount(1, 10)
            then("퀴즈 갯수가 변경된다.") {
                game.setting.quizCount shouldBe 10
            }
        }
        `when`("난이도를 변경하면") {
            game.updateLevel(1, GameLevel.HARD)
            then("난이도가 변경된다.") {
                game.setting.level shouldBe GameLevel.HARD
            }
        }
    }

    given("랜덤 게임방 상태 확인") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val user2 = User(2, "황민우", "https://cdn.example.com/v2/users/2/profile.webp")
        val user3 = User(3, "윤수빈", "https://cdn.example.com/v2/users/3/profile.webp")
        val user4 = User(4, "강지원", "https://cdn.example.com/v2/users/4/profile.webp")
        val user5 = User(5, "장원석", "https://cdn.example.com/v2/users/5/profile.webp")

        val randomGame = Game(
            1,
            GameType.RANDOM,
            GameSetting(
                GameSubject.ALGORITHM,
                GameLevel.EASY,
                5
            ),
            GameStatus.WAITING,
            Players(
                listOf(
                    Player(user, PlayerRole.HOST, PlayerStatus.JOINED),
                    Player(user2, PlayerRole.GUEST, PlayerStatus.JOINED),
                    Player(user3, PlayerRole.GUEST, PlayerStatus.JOINED),
                    Player(user4, PlayerRole.GUEST, PlayerStatus.JOINED),
                    Player(user5, PlayerRole.GUEST, PlayerStatus.JOINED)
                )
            ),
            null
        )

        `when`("모든 참가자의 준비 상태를 확인하면") {
            val ready = randomGame.isReady()
            then("모든 참가자가 준비 상태이면 true를 반환한다.") {
                ready shouldBe true
            }
        }
    }

})
