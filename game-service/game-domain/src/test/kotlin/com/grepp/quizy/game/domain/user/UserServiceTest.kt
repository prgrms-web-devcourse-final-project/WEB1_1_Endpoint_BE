package com.grepp.quizy.game.domain.user

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UserServiceTest : BehaviorSpec({

    val userReader = mockk<UserReader>()
    val userService = UserService(userReader)

    given("유저 레이팅 조회") {
        every { userReader.readRating(1) } returns 1500

        `when`("유저 레이팅을 조회할 때") {
            val userRating = userService.getUserRating(1)

            then("유저 레이팅이 조회되어야 한다.") {
                userRating shouldBe 1500
            }
        }
    }

})