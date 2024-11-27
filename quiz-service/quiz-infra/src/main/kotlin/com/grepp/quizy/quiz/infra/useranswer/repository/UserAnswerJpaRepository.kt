package com.grepp.quizy.quiz.infra.useranswer.repository

import com.grepp.quizy.quiz.infra.useranswer.entity.UserAnswerEntity
import com.grepp.quizy.quiz.infra.useranswer.entity.UserAnswerEntityId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserAnswerJpaRepository : JpaRepository<UserAnswerEntity, UserAnswerEntityId> {
        @Query("SELECT ua.id.quizId FROM UserAnswerEntity ua WHERE ua.id.userId = :userId")
        fun findAllQuizIdByUserId(userId: Long): List<Long>
}
