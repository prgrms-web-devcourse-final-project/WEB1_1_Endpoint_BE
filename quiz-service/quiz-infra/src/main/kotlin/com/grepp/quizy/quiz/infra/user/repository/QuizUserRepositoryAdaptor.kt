package com.grepp.quizy.quiz.infra.user.repository

import com.grepp.quizy.quiz.domain.user.QuizUser
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.domain.user.QuizUserRepository
import com.grepp.quizy.quiz.infra.user.entity.QuizUserEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class QuizUserRepositoryAdaptor(
    private val quizUserJpaRepository: QuizUserJpaRepository
) : QuizUserRepository {

    override fun save(quizUser: QuizUser) {
        quizUserJpaRepository.save(QuizUserEntity.from(quizUser))
    }

    override fun findById(id: UserId): QuizUser? {
        return quizUserJpaRepository.findByIdOrNull(id.value)
            ?.toDomain()
    }

    override fun findByIdIn(userIds: List<UserId>) =
        quizUserJpaRepository.findAllById(userIds.map { it.value })
            .map { it.toDomain() }

    override fun deleteById(id: UserId) {
        quizUserJpaRepository.deleteById(id.value)
    }
}