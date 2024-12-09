package com.grepp.quizy.game.domain.game

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class InviteCodeTest : BehaviorSpec({

    given("초대 코드") {
        `when`("초대 코드를 생성하면") {
            val inviteCode = InviteCode.generate()
            then("6자리 초대 코드가 생성되어야 한다.") {
                inviteCode.length shouldBe 6
            }
            then("초대코드는 숫자와 영문으로 구성되어야 한다.") {
                inviteCode.matches(Regex("[a-zA-Z0-9]+")) shouldBe true
            }
        }
    }

})