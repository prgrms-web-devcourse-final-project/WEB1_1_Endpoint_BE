package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.user.User
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class PlayerTest : BehaviorSpec({

    given("플레이어가") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")

        val player = Player(user, PlayerRole.GUEST, PlayerStatus.WAITING)
        `when`("게임에 참여할 때") {
            player.join()
            then("플레이어의 상태가 참가상태로 변경되어야 한다.") {
                player.status shouldBe PlayerStatus.JOINED
            }
        }
    }

    given("플레이어가") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")

        val player = Player(user, PlayerRole.GUEST, PlayerStatus.WAITING)
        `when`("방장 권한을 줄 때") {
            player.grantHost()
            then("플레이어는 방장이 되어야 한다.") {
                player.role shouldBe PlayerRole.HOST
            }
        }
    }

    given("플레이어가") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")

        val player = Player(user, PlayerRole.GUEST, PlayerStatus.WAITING)
        `when`("방장인지 확인하면") {
            player.isHost()
            then("플레이어가 방장인지 확인해야 한다.") {
                player.isHost() shouldBe false
            }
        }
        `when`("게스트인지 확인하면") {
            player.isGuest()
            then("플레이어가 게스트인지 확인해야 한다.") {
                player.isGuest() shouldBe true
            }
        }
    }

    given("플레이어가") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")

        val player = Player(user, PlayerRole.GUEST, PlayerStatus.WAITING)
        `when`("대기 상태인지 확인하면") {
            player.isWaiting()
            then("플레이어가 대기 상태인지 확인해야 한다.") {
                player.isWaiting() shouldBe true
            }
        }
    }

})