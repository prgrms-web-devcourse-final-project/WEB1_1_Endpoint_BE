package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.exception.GameException
import com.grepp.quizy.game.domain.user.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class PlayersTest : BehaviorSpec({

    given("플레이어 목록에") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val user2 = User(2, "황민우", "https://cdn.example.com/v2/users/2/profile.webp")
        val user3 = User(3, "윤수빈", "https://cdn.example.com/v2/users/3/profile.webp")
        val user4 = User(4, "강지원", "https://cdn.example.com/v2/users/4/profile.webp")

        val players = Players(
            listOf(
                Player(
                    user,
                    PlayerRole.HOST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user2,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user3,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                ),
            )
        )

        `when`("플레이어를 추가할 때") {
            val addedPlayers = players.add(
                Player(
                    user4,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                )
            )
            then("플레이어가 추가되어야 한다.") {
                addedPlayers.players.size shouldBe 4
            }
        }
    }

    given("가득찬 플레이어 목록에") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val user2 = User(2, "황민우", "https://cdn.example.com/v2/users/2/profile.webp")
        val user3 = User(3, "윤수빈", "https://cdn.example.com/v2/users/3/profile.webp")
        val user4 = User(4, "강지원", "https://cdn.example.com/v2/users/4/profile.webp")
        val user5 = User(5, "장원석", "https://cdn.example.com/v2/users/5/profile.webp")
        val user6 = User(6, "최훈", "https://cdn.example.com/v2/users/6/profile.webp")

        val players = Players(
            listOf(
                Player(
                    user,
                    PlayerRole.HOST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user2,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user3,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user4,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user5,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                )
            )
        )
        `when`("플레이어를 추가할 때") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameAlreadyFullException> {
                    players.add(
                        Player(
                            user6,
                            PlayerRole.GUEST,
                            PlayerStatus.JOINED
                        )
                    )
                }
            }
        }
    }

    given("이미 존재하는 사용자 목록에") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val user2 = User(2, "황민우", "https://cdn.example.com/v2/users/2/profile.webp")
        val user3 = User(3, "윤수빈", "https://cdn.example.com/v2/users/3/profile.webp")
        val user4 = User(4, "강지원", "https://cdn.example.com/v2/users/4/profile.webp")

        val players = Players(
            listOf(
                Player(
                    user,
                    PlayerRole.HOST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user2,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user3,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user4,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                )
            )
        )
        `when`("동일한 플레이어를 추가하면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameAlreadyParticipatedException> {
                    players.add(
                        Player(
                            user,
                            PlayerRole.GUEST,
                            PlayerStatus.JOINED
                        )
                    )
                }
            }
        }
    }

    given("플레이어 목록에서") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val user2 = User(2, "황민우", "https://cdn.example.com/v2/users/2/profile.webp")
        val user3 = User(3, "윤수빈", "https://cdn.example.com/v2/users/3/profile.webp")
        val user4 = User(4, "강지원", "https://cdn.example.com/v2/users/4/profile.webp")
        val user5 = User(5, "장원석", "https://cdn.example.com/v2/users/5/profile.webp")
        val user6 = User(6, "최훈", "https://cdn.example.com/v2/users/6/profile.webp")

        val players = Players(
            listOf(
                Player(
                    user,
                    PlayerRole.HOST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user2,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user3,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user4,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                )
            )
        )
        `when`("플레이어를 제거하면") {
            val removedPlayers = players.remove(
                Player(
                    user4,
                    PlayerRole.HOST,
                    PlayerStatus.JOINED
                )
            )
            then("플레이어가 제거되어야 한다.") {
                removedPlayers.players.size shouldBe 3
            }
        }
        `when`("목록에 존재하지않는 사용자를 제거하면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameNotParticipatedException> {
                    players.remove(
                        Player(
                            user6,
                            PlayerRole.GUEST,
                            PlayerStatus.JOINED
                        )
                    )
                }
            }
        }
        `when`("방장을 목록에서 제거하면") {
            val removedPlayers = players.remove(
                Player(
                    user,
                    PlayerRole.HOST,
                    PlayerStatus.JOINED
                )
            )
            then("목록의 다음 사용자가 방장이 되어야 한다.") {
                removedPlayers.players[0].role shouldBe PlayerRole.HOST
                removedPlayers.players[0].user.id shouldBe 2
            }
        }
    }

    given("플레이어 목록에서") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val user2 = User(2, "황민우", "https://cdn.example.com/v2/users/2/profile.webp")
        val user3 = User(3, "윤수빈", "https://cdn.example.com/v2/users/3/profile.webp")
        val user4 = User(4, "강지원", "https://cdn.example.com/v2/users/4/profile.webp")

        val players = Players(
            listOf(
                Player(
                    user,
                    PlayerRole.HOST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user2,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user3,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user4,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                )
            )
        )
        `when`("사용자를 찾으면") {
            val findPlayer = players.findPlayer(1)
            then("해당 사용자가 조회되어야 한다.") {
                findPlayer shouldBe Player(
                    user,
                    PlayerRole.HOST,
                    PlayerStatus.JOINED
                )
            }
        }
        `when`("존재하지 않는 사용자를 찾으면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameNotParticipatedException> {
                    players.findPlayer(999)
                }
            }
        }
    }

    given("플레이어 목록에서") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val user2 = User(2, "황민우", "https://cdn.example.com/v2/users/2/profile.webp")
        val user3 = User(3, "윤수빈", "https://cdn.example.com/v2/users/3/profile.webp")
        val user4 = User(4, "강지원", "https://cdn.example.com/v2/users/4/profile.webp")

        val players = Players(
            listOf(
                Player(
                    user,
                    PlayerRole.HOST,
                    PlayerStatus.WAITING
                ),
                Player(
                    user2,
                    PlayerRole.GUEST,
                    PlayerStatus.WAITING
                ),
                Player(
                    user3,
                    PlayerRole.GUEST,
                    PlayerStatus.WAITING
                ),
                Player(
                    user4,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                )
            )
        )
        `when`("참여요청을 보내면") {
            players.joinRandomGame(1)
            then("변경된 플레이어 목록이 반환되어야 한다.") {
                players.players[0].status shouldBe PlayerStatus.JOINED
            }
        }
        `when`("이미 준비된 사용자가 참여요청을 보내면") {
            then("예외가 발생해야 한다.") {
                shouldThrow<GameException.GameAlreadyParticipatedException> {
                    players.joinRandomGame(4)
                }
            }
        }
    }

    given("플레이어 목록이") {
        val players = Players(emptyList())
        `when`("비어있다면") {
            val result = players.isEmpty()
            then("플레이어 목록이 비어있어야 한다.") {
                result shouldBe true
            }
        }
    }

    given("플레이어 목록의 모든 플레이어의") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")
        val user2 = User(2, "황민우", "https://cdn.example.com/v2/users/2/profile.webp")
        val user3 = User(3, "윤수빈", "https://cdn.example.com/v2/users/3/profile.webp")
        val user4 = User(4, "강지원", "https://cdn.example.com/v2/users/4/profile.webp")

        val players = Players(
            listOf(
                Player(
                    user,
                    PlayerRole.HOST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user2,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user3,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                ),
                Player(
                    user4,
                    PlayerRole.GUEST,
                    PlayerStatus.JOINED
                )
            )
        )
        `when`("모두 참가상태인지 확인할 때") {
            players.isAllParticipated()
            then("모두 참가상태여야 한다.") {
                players.isAllParticipated() shouldBe true
            }
        }
    }

})