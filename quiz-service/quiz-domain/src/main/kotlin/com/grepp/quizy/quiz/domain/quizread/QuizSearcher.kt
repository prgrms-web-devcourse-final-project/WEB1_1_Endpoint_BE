package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.quiz.Quiz
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerReader
import org.springframework.stereotype.Component

@Component
class QuizSearcher(
        private val userAnswerReader: UserAnswerReader,
        private val quizSearchRepository: QuizSearchRepository
) {

    fun search(condition: UserSearchCondition) =
            quizSearchRepository.search(condition)

    fun search(condition: GameQuizSearchCondition) =
            quizSearchRepository.search(condition)

    fun searchUnanswered(userId: UserId, condition: UserSearchCondition): Slice<Quiz> {
        val answeredQuizIds = userAnswerReader.readAnswered(userId)
        return quizSearchRepository.searchNotIn(answeredQuizIds, condition)
    }

    fun searchByCategoryUnanswered(userId: UserId, condition: FeedSearchCondition): Slice<Quiz> {
        val answeredQuizIds = userAnswerReader.readAnswered(userId)
        return quizSearchRepository.searchNotIn(answeredQuizIds, condition)
    }

    fun searchByCategory(condition: FeedSearchCondition): Slice<Quiz> {
        return quizSearchRepository.searchNotIn(condition)
    }
}
