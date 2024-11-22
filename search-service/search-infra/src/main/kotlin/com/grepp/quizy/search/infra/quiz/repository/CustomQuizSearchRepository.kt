package com.grepp.quizy.search.infra.quiz.repository

import com.grepp.quizy.search.infra.quiz.document.QuizDocument
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface CustomQuizSearchRepository {

    fun search(
            keyword: String,
            pageable: Pageable,
    ): Slice<QuizDocument>

    fun searchAnswerableQuiz(
        category: String,
        difficultyCriteria: Double,
        pageable: Pageable,
    ): List<QuizDocument>

    fun searchUserAnswer(
            userId: Long,
            quizIds: List<Long>,
    ): Map<Long, Int>
}
