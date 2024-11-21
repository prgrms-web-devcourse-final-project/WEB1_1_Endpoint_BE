package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.common.dto.DateTime
import com.grepp.quizy.quiz.domain.useranswer.UserId

class MultipleChoiceQuiz
private constructor(
        userId: UserId,
        content: QuizContent,
        private var _answer: QuizAnswer,
        dateTime: DateTime = DateTime.init(),
        type: QuizType = QuizType.MULTIPLE_CHOICE,
        id: QuizId = QuizId(0),
        commentCount: Long = 0,
) :
        Quiz(userId, type, content, id, dateTime, commentCount),
        Answerable {

    val answer: QuizAnswer
        get() = _answer

    init {
        validateOptions(4)
        validateAnswer()
    }

    companion object {
        fun create(
                userId: UserId,
                content: QuizContent,
                answer: QuizAnswer,
        ): MultipleChoiceQuiz {
            return MultipleChoiceQuiz(userId, content, answer)
        }

        fun of(
                userId: UserId,
                content: QuizContent,
                answer: QuizAnswer,
                id: QuizId,
                dateTime: DateTime,
                commentCount: Long,
        ): MultipleChoiceQuiz {
            return MultipleChoiceQuiz(
                    userId = userId,
                    content = content,
                    _answer = answer,
                    id = id,
                    dateTime = dateTime,
                    commentCount = commentCount,
            )
        }
    }

    override fun validateAnswer() {
        require(
                answer.value in
                        content.options.indices.map { it.toString() }
        ) {
            "객관식 퀴즈의 답은 0부터 ${content.options.size - 1} 사이의 값이어야 합니다"
        }
    }

    override fun getQuizAnswer(): QuizAnswer {
        return answer
    }

    override fun updateAnswer(newAnswer: QuizAnswer): Answerable {
        _answer = newAnswer
        return this
    }
}
