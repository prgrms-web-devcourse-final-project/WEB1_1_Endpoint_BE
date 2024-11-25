package com.grepp.quizy.quiz.infra.quizread.repository

import com.grepp.quizy.quiz.domain.quizread.QuizDifficultyType
import com.grepp.quizy.quiz.infra.quizread.document.QuizDocument
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface CustomQuizSearchRepository {

    fun search(
            keyword: String,
            pageable: Pageable,
    ): Slice<QuizDocument>

    fun searchAnswerableQuiz(
        category: String,
        difficulty: QuizDifficultyType,
        pageable: Pageable,
    ): List<QuizDocument>

    fun searchUserAnswer(
            userId: Long,
            quizIds: List<Long>,
    ): Map<Long, Int>
}
