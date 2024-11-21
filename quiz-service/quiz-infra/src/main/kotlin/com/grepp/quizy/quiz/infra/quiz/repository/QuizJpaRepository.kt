package com.grepp.quizy.quiz.infra.quiz.repository

import com.grepp.quizy.quiz.infra.quiz.entity.QuizEntity
import jakarta.persistence.LockModeType
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface QuizJpaRepository :
        JpaRepository<QuizEntity, Long>, QuizJpaBatchRepository {

    @Query(
            "select exists(select 1 from UserAnswerEntity ua where ua.id.quizId = :value)"
    )
    fun existsUserAnswerByQuizId(value: Long): Boolean

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select q from QuizEntity q where q.id = :value")
    fun findByIdWithLock(value: Long): Optional<QuizEntity>
}
