package com.grepp.quizy.infra.game.websocket

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class WebSocketPrincipalTest : DescribeSpec({

    describe("웹소켓 사용자에서") {
        val principal = WebSocketPrincipal("minhyeok")
        context("이름을 가져오면") {
            val name = principal.name
            it("이름을 반환한다.") {
                name shouldBe "minhyeok"
            }
        }
    }
})