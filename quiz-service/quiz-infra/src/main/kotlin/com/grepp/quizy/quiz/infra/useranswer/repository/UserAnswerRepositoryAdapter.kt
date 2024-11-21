package com.grepp.quizy.quiz.infra.useranswer.repository

import com.grepp.quizy.quiz.domain.useranswer.UserAnswer
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerRepository
import com.grepp.quizy.quiz.infra.useranswer.entity.UserAnswerEntity
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class UserAnswerRepositoryAdapter(
        private val userAnswerJpaRepository: UserAnswerJpaRepository
) : UserAnswerRepository {

    override fun save(userAnswer: UserAnswer): UserAnswer {
        return userAnswerJpaRepository
                .save(UserAnswerEntity.from(userAnswer))
                .toDomain()
    }
}
