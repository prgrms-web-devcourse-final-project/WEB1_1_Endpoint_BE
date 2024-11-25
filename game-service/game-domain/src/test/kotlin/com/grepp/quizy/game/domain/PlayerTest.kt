package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.game.Player
import com.grepp.quizy.game.domain.game.PlayerRole.GUEST
import com.grepp.quizy.game.domain.game.PlayerRole.HOST
import com.grepp.quizy.game.domain.game.PlayerStatus
import com.grepp.quizy.game.domain.user.User
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class PlayerTest() : DescribeSpec({
    describe("Player") {
        context("플레이어를 생성하면") {
            it("기본값은 게스트로 생성한다.") {
                val player = Player(user)

                player.user.id shouldBe 1
                player.role shouldBe GUEST
            }
            it("호스트로 생성한다.") {
                val player = Player(user, HOST)

                player.user.id shouldBe 1
                player.role shouldBe HOST
            }
        }
        context("플레이어의 역할을 변경하면") {
            it("호스트 권한을 부여한다.") {
                val player = Player(user)

                player.grantHost()

                player.role shouldBe HOST
            }
        }
        context("역할을 확인하면") {
            it("호스트인지 확인 할 수 있다.") {
                val player = Player(user, HOST)

                player.isHost() shouldBe true
            }
            it("게스트인지 확인 할 수 있다.") {
                val player = Player(user, GUEST)

                player.isGuest() shouldBe true
            }
        }
        context("플레이어의 정보가 같다면") {
            it("같은 플레이어로 판단한다.") {
                val player1 = Player(user, HOST)
                val player2 = Player(user, GUEST)

                player1 shouldBe player2
            }
        }
        context("게임에 참여하면") {
            it("참여 상태로 변경한다.") {
                val player = Player(user)

                player.join()

                player.status shouldBe PlayerStatus.JOINED
            }
        }
    }
}) {
    companion object {
        val user = User(1, "프로게이머", "imgPath")
    }
}

