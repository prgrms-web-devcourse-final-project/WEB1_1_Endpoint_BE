package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.game.InviteCode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class InviteCodeTest() : DescribeSpec({
    describe("InviteCode") {
        context("초대 코드를 생성하면") {
            it("6자리 코드를 생성한다.") {
                val inviteCode = InviteCode()

                inviteCode.value.length shouldBe 6
            }

            it("대문자 알파벳과 숫자로만 구성되어야 한다.") {
                val inviteCode = InviteCode()

                inviteCode.value.all { it in ('A'..'Z') || it in ('0'..'9') } shouldBe true
            }

            it("매번 새로운 코드가 생성되어야 한다.") {
                // 중복된 값이 존재 할수도 있으나 최소 100개의 코드를 생성했을 때 중복된 값이 없어야 한다.
                val inviteCodes = List(100) { InviteCode().value }
                val uniqueInviteCodes = inviteCodes.toSet()

                uniqueInviteCodes.size shouldBe inviteCodes.size
            }

            context("직접 값을 지정하면") {
                it("지정된 값으로 초대 코드가 생성된다.") {
                    val code = "ABC123"
                    val inviteCode = InviteCode(code)

                    inviteCode.value shouldBe code
                }
            }
        }

        context("generate 함수를 호출하면") {
            it("매번 다른 코드를 생성한다.") {
                val code1 = InviteCode.Companion.generate()
                val code2 = InviteCode.Companion.generate()

                code1 shouldNotBe code2
            }

            it("지정된 문자로만 구성된 코드를 생성한다.") {
                val code = InviteCode.Companion.generate()
                val allowedChars = ('A'..'Z') + ('0'..'9')

                code.all { it in allowedChars } shouldBe true
            }
        }
    }
}) {
}