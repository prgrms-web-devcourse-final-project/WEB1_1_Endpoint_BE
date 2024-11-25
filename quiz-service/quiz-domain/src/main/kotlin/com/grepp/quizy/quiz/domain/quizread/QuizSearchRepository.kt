package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.global.dto.Slice

interface QuizSearchRepository {
    fun search(condition: UserSearchCondition): Slice<QuizForRead>

    fun search(condition: GameQuizSearchCondition): List<AnswerableQuiz>
}
