package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.common.dto.SliceResult
import com.grepp.quizy.quiz.domain.quiz.QuizReader
import org.springframework.stereotype.Component

@Component
class UserAnswerCombiner(
    private val quizReader: QuizReader
) {
    fun combineWithQuiz(userAnswers: SliceResult<UserAnswer>): SliceResult<QuizWithUserAnswer> {
        val quizIds = userAnswers.content.map { it.key.quizId }
        val quizzes = quizReader.readIn(quizIds).associateBy { it.id }
        return userAnswers.content.mapNotNull { userAnswer ->
            quizzes[userAnswer.key.quizId]?.let { QuizWithUserAnswer(it, userAnswer) }
        }.let { quizWithUserAnswers ->
            SliceResult(quizWithUserAnswers.sortedBy { it.userAnswer.answeredAt }, userAnswers.hasNext)
        }
    }
}