package com.grepp.quizy.quiz.infra.quiz.repository

import com.grepp.quizy.quiz.infra.quiz.entity.QuizEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuizJpaRepository : JpaRepository<QuizEntity, Long> {

    @Query(
            "select exists(select 1 from UserAnswerEntity ua where ua.id.quizId = :value)"
    )
    fun existsUserAnswerByQuizId(value: Long): Boolean
}
