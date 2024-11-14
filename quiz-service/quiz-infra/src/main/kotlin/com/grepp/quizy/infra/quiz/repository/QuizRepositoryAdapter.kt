package com.grepp.quizy.infra.quiz.repository

import com.grepp.quizy.domain.quiz.Quiz
import com.grepp.quizy.domain.quiz.QuizRepository
import com.grepp.quizy.infra.quiz.entity.QuizEntity
import org.springframework.stereotype.Repository

@Repository
class QuizRepositoryAdapter(private val quizJpaRepository: QuizJpaRepository) :
        QuizRepository {
    override fun save(quiz: Quiz): Quiz {
        return quizJpaRepository.save(QuizEntity.from(quiz)).toDomain()
    }
}
