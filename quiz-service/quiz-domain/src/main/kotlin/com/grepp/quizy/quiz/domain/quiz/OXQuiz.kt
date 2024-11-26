package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.common.dto.DateTime
import com.grepp.quizy.quiz.domain.user.UserId

class OXQuiz
private constructor(
        userId: UserId,
        content: QuizContent,
        private var _answer: QuizAnswer,
        dateTime: DateTime = DateTime.init(),
        type: QuizType = QuizType.OX,
        id: QuizId = QuizId(0),
        commentCount: Long = 0,
) :
        Quiz(userId, type, content, id, dateTime, commentCount),
        Answerable {

    val answer: QuizAnswer
        get() = _answer

    init {
        validateOptions(2)
        validateAnswer()
    }

    companion object {
        fun create(
                userId: UserId,
                content: QuizContent,
                answer: QuizAnswer,
        ): OXQuiz {
            return OXQuiz(userId, content, answer)
        }

        fun of(
                userId: UserId,
                content: QuizContent,
                answer: QuizAnswer,
                id: QuizId,
                dateTime: DateTime,
                commentCount: Long,
        ): OXQuiz {
            return OXQuiz(
                    userId = userId,
                    content = content,
                    _answer = answer,
                    id = id,
                    dateTime = dateTime,
                    commentCount = commentCount,
            )
        }
    }

    override fun getQuizAnswer(): QuizAnswer {
        return answer
    }

    override fun validateAnswer() {
        require(answer.value in listOf("O", "X")) {
            "OX 퀴즈의 답은 O 또는 X 여야 합니다"
        }
    }

    override fun updateAnswer(newAnswer: QuizAnswer): Answerable {
        _answer = newAnswer
        return this
    }
}
