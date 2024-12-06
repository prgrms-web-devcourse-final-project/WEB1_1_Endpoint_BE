package com.grepp.quizy.game.domain.quiz

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class QuizReaderTest : BehaviorSpec({

    val quizRepository = mockk<QuizRepository>()
    val quizReader = QuizReader(quizRepository)

    given("퀴즈 조회") {
        val now = System.currentTimeMillis()
        every { quizRepository.findById(1) } returns
                GameQuiz(
                    1,
                    "OS는 운영체제의 약자이다",
                    listOf(
                        GameQuizOption(1, "X"),
                        GameQuizOption(2, "O")
                    ),
                    GameQuizAnswer("O", "운영체제는 영어로 Operating System이기 때문에 약자가 맞다."),
                    now,
                )
        `when`("퀴즈를 조회할 때") {
            val gameQuiz = quizReader.read(1)
            then("퀴즈가 조회되어야 한다.") {
                with(gameQuiz) {
                    id shouldBe 1
                    content shouldBe "OS는 운영체제의 약자이다"
                    options.size shouldBe 2
                    options[0].no shouldBe 1
                    options[0].content shouldBe "X"
                    options[1].no shouldBe 2
                    options[1].content shouldBe "O"
                    answer shouldBe GameQuizAnswer("O", "운영체제는 영어로 Operating System이기 때문에 약자가 맞다.")
                    submitTimestamp shouldBe now
                }
                verify(exactly = 1) { quizRepository.findById(1) }
            }
        }
    }

})