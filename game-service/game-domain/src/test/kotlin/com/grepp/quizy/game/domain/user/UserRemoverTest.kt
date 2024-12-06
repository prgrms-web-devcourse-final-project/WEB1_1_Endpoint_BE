package com.grepp.quizy.game.domain.user

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class UserRemoverTest : BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val userRemover = UserRemover(userRepository)

    given("유저 삭제") {
        every { userRepository.deleteById(any()) } returns Unit

        `when`("유저를 삭제할 때") {
            userRemover.remove(1)
            then("유저가 삭제되어야 한다.") {
                verify(exactly = 1) { userRepository.deleteById(1) }
            }
        }
    }
})