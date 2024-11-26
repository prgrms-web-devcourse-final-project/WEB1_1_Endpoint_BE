package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.quiz.Quiz

interface QuizSearchRepository {
    fun search(condition: UserSearchCondition): Slice<Quiz>

    fun search(condition: GameQuizSearchCondition): List<Quiz>
}
