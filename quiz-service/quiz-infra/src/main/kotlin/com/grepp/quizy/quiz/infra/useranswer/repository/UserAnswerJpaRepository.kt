package com.grepp.quizy.quiz.infra.useranswer.repository

import com.grepp.quizy.quiz.infra.useranswer.entity.UserAnswerEntity
import com.grepp.quizy.quiz.infra.useranswer.entity.UserAnswerEntityId
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserAnswerJpaRepository : JpaRepository<UserAnswerEntity, UserAnswerEntityId> {
        @Query("SELECT ua.id.quizId FROM UserAnswerEntity ua WHERE ua.id.userId = :userId")
        fun findAllQuizIdByUserId(userId: Long): List<Long>


        @Query("SELECT ua FROM UserAnswerEntity ua WHERE ua.id.userId = :userId AND ua.isCorrect = :correct")
        fun findAllByUserIdAndIsCorrect(userId: Long, correct: Boolean, pageable: Pageable): Slice<UserAnswerEntity>
}
