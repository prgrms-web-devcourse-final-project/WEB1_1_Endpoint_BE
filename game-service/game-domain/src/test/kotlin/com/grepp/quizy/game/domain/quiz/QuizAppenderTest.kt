package com.grepp.quizy.game.domain.quiz

import com.grepp.quizy.game.domain.game.IdGenerator
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class QuizAppenderTest : BehaviorSpec({

    given("퀴즈 목록 추가") {
        val quizRepository = mockk<QuizRepository>()
        val idGenerator = mockk<IdGenerator>()
        val quizAppender = QuizAppender(quizRepository, idGenerator)

        val inputQuizzes = listOf(
            GameQuiz(
                id = 957,
                content = "OS는 운영체제의 약자이다",
                answer = GameQuizAnswer("O", "운영체제는 영어로 Operating System이기 때문에 약자가 맞다."),
                options = listOf(
                    GameQuizOption(1, "X"),
                    GameQuizOption(2, "O")
                ),
            ),
            GameQuiz(
                id = 1234,
                content = "JVM은 Java Virtual Machine의 약자이다",
                answer = GameQuizAnswer("O", "JVM은 Java Virtual Machine의 약자이다."),
                options = listOf(
                    GameQuizOption(425, "O"),
                    GameQuizOption(426, "X")
                ),
            )
        )

        val expectedNewIds = listOf(1L, 2L)
        val expectedQuizzes = inputQuizzes.mapIndexed { index, quiz ->
            GameQuiz(
                id = expectedNewIds[index],
                content = quiz.content,
                answer = quiz.answer,
                options = quiz.options,
                submitTimestamp = quiz.submitTimestamp
            )
        }

        every {
            idGenerator.generate("quiz")
        } returnsMany expectedNewIds

        every {
            quizRepository.saveAll(any<List<GameQuiz>>())
        } returnsArgument 0

        `when`("퀴즈 목록을 추가하면") {
            val result = quizAppender.appendAll(inputQuizzes)

            then("새로운 ID가 생성되어야 한다") {
                result.map { it.id } shouldContainExactly expectedNewIds
            }

            then("입력된 퀴즈의 내용은 유지되어야 한다") {
                result.forEachIndexed { index, quiz ->
                    val inputQuiz = inputQuizzes[index]
                    quiz.content shouldBe inputQuiz.content
                    quiz.answer shouldBe inputQuiz.answer
                    quiz.options shouldBe inputQuiz.options
                    quiz.submitTimestamp shouldBe inputQuiz.submitTimestamp
                }
            }

            then("Repository에 저장되어야 한다") {
                verify(exactly = 1) {
                    quizRepository.saveAll(withArg { savedQuizzes ->
                        savedQuizzes shouldHaveSize 2
                        savedQuizzes.forEachIndexed { index, savedQuiz ->
                            val expectedQuiz = expectedQuizzes[index]
                            savedQuiz.id shouldBe expectedQuiz.id
                            savedQuiz.content shouldBe expectedQuiz.content
                            savedQuiz.answer shouldBe expectedQuiz.answer
                            savedQuiz.options shouldBe expectedQuiz.options
                            savedQuiz.submitTimestamp shouldBe expectedQuiz.submitTimestamp
                        }
                    })
                }
            }

            then("ID Generator가 각 퀴즈마다 호출되어야 한다") {
                verify(exactly = 2) {
                    idGenerator.generate("quiz")
                }
            }
        }
    }

})