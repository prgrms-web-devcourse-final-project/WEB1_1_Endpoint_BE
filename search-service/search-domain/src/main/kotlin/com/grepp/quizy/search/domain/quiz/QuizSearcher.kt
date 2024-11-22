package com.grepp.quizy.search.domain.quiz

import com.grepp.quizy.search.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class QuizSearcher(
        private val quizSearchRepository: QuizSearchRepository
) {

    fun search(condition: UserSearchCondition) =
            quizSearchRepository.search(condition)

    fun search(condition: GameSearchCondition) =
            quizSearchRepository.search(condition)

    fun searchUserAnswer(userId: UserId, quizIds: List<QuizId>) =
            quizSearchRepository.searchUserAnswer(userId, quizIds)
}
