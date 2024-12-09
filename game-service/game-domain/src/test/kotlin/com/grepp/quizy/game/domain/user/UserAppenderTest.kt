package com.grepp.quizy.game.domain.user

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class UserAppenderTest: BehaviorSpec({

    val userRepository = mockk<UserRepository>()
    val userAppender = UserAppender(userRepository)

    given("유저 추가") {
        val user = User(1, "나민혁", "https://cdn.example.com/v2/users/1/profile.webp")

        every { userRepository.save(any()) } returns user

        `when`("유저를 추가할 때") {
            val appendedUser = userAppender.append(user)
            then("유저가 추가되어야 한다.") {
                appendedUser.id shouldBe  1
                appendedUser.name shouldBe "나민혁"
                appendedUser.imgPath shouldBe "https://cdn.example.com/v2/users/1/profile.webp"

                verify(exactly = 1) { userRepository.save(user) }
            }
        }
    }
}) {

}