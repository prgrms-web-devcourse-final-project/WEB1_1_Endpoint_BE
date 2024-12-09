package com.grepp.quizy.game.domain.useranswer

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class UserAnswerReaderTest : BehaviorSpec({
    val userAnswerRepository = mockk<UserAnswerRepository>()
    val userAnswerReader = UserAnswerReader(userAnswerRepository)

    given("유저 답안 목록 조회") {
        val userAnswer1 = UserAnswer(
            1,
            1,
            "OS는 운영체제의 약자이다",
            "O",
            "O",
            "운영체제는 영어로 Operating System이기 때문에 약자가 맞다.",
            true
        )
        val userAnswer2 = UserAnswer(
            1,
            1,
            "Java와 JavaScript는 같은 언어이다",
            "O",
            "X",
            "Java는 컴파일 언어이고 JavaScript는 인터프리터 언어이다.",
            false
        )
        val userAnswers = listOf(
            userAnswer1, userAnswer2
        )

        context("유저의 답안이 존재하는 경우") {
            every {
                userAnswerRepository.findAllByGameIdAndUserId(1, 1)
            } returns userAnswers

            `when`("유저 답안 목록을 조회하면") {
                val result = userAnswerReader.readAll(1, 1)

                then("해당 유저의 모든 답안이 조회되어야 한다") {
                    result shouldHaveSize 2
                    result[0] shouldBe userAnswer1
                    result[1] shouldBe userAnswer2

                    verify(exactly = 1) {
                        userAnswerRepository.findAllByGameIdAndUserId(1, 1)
                    }
                }
            }
        }

        context("유저의 답안이 존재하지 않는 경우") {
            every {
                userAnswerRepository.findAllByGameIdAndUserId(999, 999)
            } returns emptyList()

            `when`("유저 답안 목록을 조회하면") {
                val result = userAnswerReader.readAll(999, 999)

                then("빈 리스트가 반환되어야 한다") {
                    result shouldBe emptyList()

                    verify(exactly = 1) {
                        userAnswerRepository.findAllByGameIdAndUserId(999, 999)
                    }
                }
            }
        }
    }

})
