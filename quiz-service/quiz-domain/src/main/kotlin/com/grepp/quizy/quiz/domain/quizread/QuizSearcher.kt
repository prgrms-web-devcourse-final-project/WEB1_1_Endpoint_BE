package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class QuizSearcher(
        private val quizSearchRepository: QuizSearchRepository
) {

    fun search(condition: UserSearchCondition) =
            quizSearchRepository.search(condition)

    fun search(condition: GameQuizSearchCondition) =
            quizSearchRepository.search(condition)

    fun searchUserAnswer(userId: UserId, quizIds: List<QuizId>) =
            quizSearchRepository.searchUserAnswer(userId, quizIds)
}
