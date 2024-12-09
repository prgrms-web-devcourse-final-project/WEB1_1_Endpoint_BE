package com.grepp.quizy.game.domain.useranswer

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class UserAnswerAppenderTest : BehaviorSpec({
    val userAnswerRepository = mockk<UserAnswerRepository>()
    val userAnswerAppender = UserAnswerAppender(userAnswerRepository)

    given("유저 답안 추가") {
        val userAnswer = UserAnswer(
            1,
            1,
            "OS는 운영체제의 약자이다",
            "O",
            "O",
            "운영체제는 영어로 Operating System이기 때문에 약자가 맞다.",
            true
        )
        every { userAnswerRepository.save((any())) } returns
                userAnswer
        `when`("유저 답안을 추가할 때") {
            val appendedUserAnswer = userAnswerAppender.append(userAnswer)
            then("유저 답안이 추가되어야 한다.") {
                verify { userAnswerRepository.save(userAnswer) }

                appendedUserAnswer shouldBe userAnswer
            }
        }
    }
})