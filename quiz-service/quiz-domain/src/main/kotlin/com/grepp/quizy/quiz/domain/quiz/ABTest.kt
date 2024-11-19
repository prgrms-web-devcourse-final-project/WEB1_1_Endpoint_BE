package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.common.dto.DateTime

class ABTest
private constructor(
        content: QuizContent,
        dateTime: DateTime = DateTime.init(),
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

        fun of(content: QuizContent, id: QuizId, dateTime: DateTime): ABTest {
            return ABTest(content = content, id = id, dateTime = dateTime)
        }
    }
}
