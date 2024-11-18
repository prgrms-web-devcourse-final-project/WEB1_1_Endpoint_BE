package com.grepp.quizy.search.infra.quiz

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface CustomQuizSearchRepository {

    fun search(keyword: String, pageable: Pageable): Slice<QuizDocument>

    fun searchUserAnswer(userId: Long, quizIds: List<Long>): Map<Long, Int>
}