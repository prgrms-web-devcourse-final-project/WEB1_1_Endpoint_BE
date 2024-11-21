package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.common.dto.DateTime
import com.grepp.quizy.quiz.domain.useranswer.UserId

class ABTest
private constructor(
        userId: UserId,
        content: QuizContent,
        dateTime: DateTime = DateTime.init(),
        type: QuizType = QuizType.AB_TEST,
        id: QuizId = QuizId(0),
        commentCount: Long = 0,
) : Quiz(userId, type, content, id, dateTime, commentCount) {

    init {
        validateOptions(2)
    }

    companion object {
        fun create(userId: UserId, content: QuizContent): ABTest {
            return ABTest(userId, content)
        }

        fun of(
                userId: UserId,
                content: QuizContent,
                id: QuizId,
                dateTime: DateTime,
                commentCount: Long,
        ): ABTest {
            return ABTest(
                    userId = userId,
                    content = content,
                    id = id,
                    dateTime = dateTime,
                    commentCount = commentCount,
            )
        }
    }
}
