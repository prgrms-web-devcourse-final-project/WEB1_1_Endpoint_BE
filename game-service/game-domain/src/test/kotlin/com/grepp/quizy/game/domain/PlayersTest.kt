package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.exception.GameException.*
import com.grepp.quizy.game.domain.game.Player
import com.grepp.quizy.game.domain.game.PlayerRole.GUEST
import com.grepp.quizy.game.domain.game.PlayerRole.HOST
import com.grepp.quizy.game.domain.game.PlayerStatus
import com.grepp.quizy.game.domain.game.Players
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class PlayersTest() : DescribeSpec({
    describe("Players") {
        context("플레이어 목록을 생성하면") {
            it("플레이어 목록을 생성한다.") {
                val players = Players(listOf(Player(1, HOST), Player(2, GUEST)))

                players shouldBe Players(listOf(Player(1, HOST), Player(2, GUEST)))
            }
        }
        context("플레이어를 추가하면") {
            it("플레이어를 추가한다.") {
                val players = Players(listOf(Player(1, HOST), Player(2, GUEST)))

                val updatedPlayers = players.add(Player(3, GUEST))

                updatedPlayers shouldBe Players(
                    listOf(
                        Player(1, HOST),
                        Player(2, GUEST),
                        Player(3, GUEST)
                    )
                )
            }
        }
        context("일정인원이 넘은 상태에서 플레이어를 추가하면") {
            it("예외를 발생시킨다.") {
                val players = Players(
                    listOf(
                        Player(1, HOST),
                        Player(2, GUEST),
                        Player(3, GUEST),
                        Player(4, GUEST),
                        Player(5, GUEST)
                    )
                )
                shouldThrow<GameAlreadyFullException> { players.add(Player(6, GUEST)) }
            }
        }
        context("이미 참가한 인원을 추가하면") {
            it("예외를 발생시킨다.") {
                val players = Players(listOf(Player(1, HOST), Player(2, GUEST)))

                shouldThrow<GameAlreadyParticipatedException> { players.add(Player(2, GUEST)) }
            }
        }

        context("플레이어를 제거하면") {
            it("플레이어를 제거한다.") {
                val players = Players(
                    listOf(
                        Player(1, HOST),
                        Player(2, GUEST),
                        Player(3, GUEST)
                    )
                )

                val updatedPlayers = players.remove(Player(2, GUEST))

                updatedPlayers shouldBe Players(
                    listOf(
                        Player(1, HOST),
                        Player(3, GUEST)
                    )
                )
            }
            it("플레이어 목록이 모두 제거되면 빈 리스트를 반환한다.") {
                val players = Players(
                    listOf(
                        Player(1, HOST)
                    )
                )

                val updatedPlayers = players.remove(Player(1, HOST))

                updatedPlayers shouldBe Players(listOf())
            }
        }
        context("호스트가 제거되면") {
            it("가장 첫번째 플레이어를 호스트로 변경한다.") {
                val players = Players(
                    listOf(
                        Player(1, HOST),
                        Player(2, GUEST),
                        Player(3, GUEST)
                    )
                )

                val updatedPlayers = players.remove(Player(1, HOST))

                updatedPlayers shouldBe Players(
                    listOf(
                        Player(2, HOST),
                        Player(3, GUEST)
                    )
                )
            }
            it("이어 받을 플레이어가 없으면 빈 리스트를 반환한다.") {
                val players = Players(
                    listOf(
                        Player(1, HOST)
                    )
                )

                val updatedPlayers = players.remove(Player(1, HOST))

                updatedPlayers shouldBe Players(listOf())
            }
        }
        context("플레이어 목록에 존재하지않는 플레이어를 제거하면") {
            it("예외를 발생시킨다.") {
                val players = Players(listOf(Player(1, HOST)))

                shouldThrow<GameNotParticipatedException> { players.remove(Player(2, GUEST)) }
            }
        }

        context("플레이어를 찾으면") {
            it("찾은 플레이어를 반환한다.") {
                val players = Players(
                    listOf(
                        Player(1, HOST),
                        Player(2, GUEST),
                        Player(3, GUEST)
                    )
                )

                val player = players.findPlayerById(2)

                player shouldBe Player(2, GUEST)
            }
            it("플레이어 목록에 존재하지 않는 플레이어를 찾으면") {
                val players = Players(listOf(Player(1, HOST)))

                shouldThrow<GameNotParticipatedException> { players.findPlayerById(2) }
            }
        }

        context("플레이어 목록이 비어있다면") {
            it("빈 목록인지 확인한다.") {
                val players = Players(listOf())

                players.isEmpty() shouldBe true
            }
        }
    }

    describe("랜덤 게임 플레이어가") {
        context("사용자가 게임에 참여하면") {
            it("참여 상태로 변경한다.") {
                val players = Players(
                    listOf(
                        Player(1, GUEST, PlayerStatus.WAITING),
                        Player(2, GUEST, PlayerStatus.WAITING),
                        Player(3, GUEST, PlayerStatus.WAITING),
                        Player(4, GUEST, PlayerStatus.WAITING),
                        Player(5, GUEST, PlayerStatus.WAITING)
                    )
                )

                val updatedPlayers = players.joinRandomGame(1)

                updatedPlayers.players[0].status shouldBe PlayerStatus.JOINED
                updatedPlayers.players[1].status shouldBe PlayerStatus.WAITING
                updatedPlayers.players[2].status shouldBe PlayerStatus.WAITING
                updatedPlayers.players[3].status shouldBe PlayerStatus.WAITING
                updatedPlayers.players[4].status shouldBe PlayerStatus.WAITING
            }
        }
        context("이미 참여한 사용자가 참여하면") {
            it("예외를 발생시킨다.") {
                val players = Players(
                    listOf(
                        Player(1, GUEST, PlayerStatus.JOINED),
                        Player(2, GUEST, PlayerStatus.WAITING),
                        Player(3, GUEST, PlayerStatus.WAITING),
                        Player(4, GUEST, PlayerStatus.WAITING),
                        Player(5, GUEST, PlayerStatus.WAITING)
                    )
                )

                shouldThrow<GameAlreadyParticipatedException> { players.joinRandomGame(1) }
            }
        }
        context("참여목록에 없는 사용자가 참여하면") {
            it("예외를 발생시킨다.") {
                val players = Players(
                    listOf(
                        Player(1, GUEST, PlayerStatus.WAITING),
                        Player(2, GUEST, PlayerStatus.WAITING),
                        Player(3, GUEST, PlayerStatus.WAITING),
                        Player(4, GUEST, PlayerStatus.WAITING),
                        Player(5, GUEST, PlayerStatus.WAITING)
                    )
                )

                shouldThrow<GameNotParticipatedException> { players.joinRandomGame(6) }
            }
        }
        context("참여자의 참여여부를 확인하면") {
            context("모두 참여했다면") {
                it("참을 반환한다.") {
                    val players = Players(
                        listOf(
                            Player(1, GUEST, PlayerStatus.JOINED),
                            Player(2, GUEST, PlayerStatus.JOINED),
                            Player(3, GUEST, PlayerStatus.JOINED),
                            Player(4, GUEST, PlayerStatus.JOINED),
                            Player(5, GUEST, PlayerStatus.JOINED)
                        )
                    )

                    val isParticipated = players.isAllParticipated()
                    isParticipated shouldBe true
                }
            }
            context("대기중인 참여자가 있다면") {
                it("거짓을 반환한다.") {
                    val players = Players(
                        listOf(
                            Player(1, GUEST, PlayerStatus.JOINED),
                            Player(2, GUEST, PlayerStatus.JOINED),
                            Player(3, GUEST, PlayerStatus.JOINED),
                            Player(4, GUEST, PlayerStatus.WAITING),
                            Player(5, GUEST, PlayerStatus.JOINED)
                        )
                    )

                    val isParticipated = players.isAllParticipated()
                    isParticipated shouldBe false
                }
            }
        }
    }
}) {
}