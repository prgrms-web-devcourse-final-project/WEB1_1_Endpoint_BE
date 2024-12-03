package com.grepp.quizy.quiz.infra.quizread.repository

import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.quiz.QuizDifficulty
import com.grepp.quizy.quiz.infra.quizread.document.QuizDocument
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface CustomQuizSearchRepository {

    fun search(
            keywords: List<String>,
            pageable: Pageable,
    ): Slice<QuizDocument>

    fun searchNotIn(
        keyword: List<String>,
        pageable: Pageable,
        quizIds: List<Long>
    ): Slice<QuizDocument>

    fun searchByCategoryNotIn(
        category: String,
        pageable: Pageable,
        quizIds: List<Long>
    ): Slice<QuizDocument>

    fun searchByCategory(category: String, pageable: Pageable): Slice<QuizDocument>

    fun searchAnswerableQuiz(
        category: String,
        difficulty: QuizDifficulty,
        pageable: Pageable,
    ): List<QuizDocument>
}
