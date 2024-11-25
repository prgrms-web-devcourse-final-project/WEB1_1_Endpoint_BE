package com.grepp.quizy.game.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UserTest() : DescribeSpec({

    describe("User") {
        context("이벤트를 통해 생성하면") {
            it("유저가 생성된다.") {
                val createdEvent = UserCreatedEvent(
                    userId = 1,
                    name = "test",
                    email = "test@gmail.com",
                    profileImageUrl = "test.jpg",
                    provider = "google",
                    providerId = "1234",
                    role = "USER"
                )

                val user = User.from(createdEvent)

                user.id shouldBe 1
                user.name shouldBe "test"
                user.imgPath shouldBe "test.jpg"
                user.rating shouldBe 1500
            }
        }
    }
}) {

}