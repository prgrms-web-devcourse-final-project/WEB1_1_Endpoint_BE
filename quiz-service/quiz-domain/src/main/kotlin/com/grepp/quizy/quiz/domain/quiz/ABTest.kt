package com.grepp.quizy.quiz.domain.quiz

class ABTest
private constructor(
        content: QuizContent,
        dateTime: QuizDateTime = QuizDateTime.init(),
        type: QuizType = QuizType.AB_TEST,
        id: QuizId = QuizId(0),
) : Quiz(type, content, id, dateTime) {

    init {
        validateOptions(2)
    }

    companion object {
        fun create(content: QuizContent): ABTest {
            return ABTest(content)
        }

        fun of(
                content: QuizContent,
                id: QuizId,
                dateTime: QuizDateTime,
        ): ABTest {
            return ABTest(content = content, id = id, dateTime = dateTime)
        }
    }
}
