package com.grepp.quizy.search.domain.quiz

import com.grepp.quizy.search.domain.global.dto.SearchCondition
import com.grepp.quizy.search.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class QuizSearcher(private val quizSearchRepository: QuizSearchRepository) {

    fun search(condition: SearchCondition) = quizSearchRepository.search(condition)

    fun searchUserAnswer(userId: UserId, quizIds: List<QuizId>) =
        quizSearchRepository.searchUserAnswer(userId, quizIds)
}