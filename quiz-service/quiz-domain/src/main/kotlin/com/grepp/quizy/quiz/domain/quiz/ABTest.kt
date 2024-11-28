package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.common.dto.DateTime
import com.grepp.quizy.quiz.domain.user.UserId

class ABTest
private constructor(
    creatorId: UserId,
    content: QuizContent,
    dateTime: DateTime = DateTime.init(),
    type: QuizType = QuizType.AB_TEST,
    id: QuizId = QuizId(0),
    commentCount: Long = 0,
    likeCount: Long = 0,
) : Quiz(creatorId, type, content, id, dateTime, commentCount, likeCount) {

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
                likeCount: Long = 0,
        ): ABTest {
            return ABTest(
                    creatorId = userId,
                    content = content,
                    id = id,
                    dateTime = dateTime,
                    commentCount = commentCount,
                    likeCount = likeCount,
            )
        }
    }
}
