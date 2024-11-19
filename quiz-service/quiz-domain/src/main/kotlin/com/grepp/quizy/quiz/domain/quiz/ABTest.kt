package com.grepp.quizy.quiz.domain.quiz

class ABTest
private constructor(
        content: QuizContent,
        type: QuizType = QuizType.AB_TEST,
        id: QuizId = QuizId(0),
) : Quiz(type, content, id) {

    init {
        validateOptions(2)
    }

    companion object {
        fun create(content: QuizContent): ABTest {
            return ABTest(content)
        }

        fun of(content: QuizContent, id: QuizId): ABTest {
            return ABTest(content, id = id)
        }
    }
}
