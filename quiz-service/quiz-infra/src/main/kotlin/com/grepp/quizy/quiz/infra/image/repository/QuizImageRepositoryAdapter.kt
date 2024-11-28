package com.grepp.quizy.quiz.infra.image.repository

import com.grepp.quizy.quiz.domain.image.QuizImage
import com.grepp.quizy.quiz.domain.image.QuizImageId
import com.grepp.quizy.quiz.domain.image.QuizImageRepository
import com.grepp.quizy.quiz.infra.image.entity.QuizImageEntity
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
@Transactional
class QuizImageRepositoryAdapter(
    private val quizImageJpaRepository: QuizImageJpaRepository
) : QuizImageRepository {

    override fun save(image: QuizImage): QuizImage {
        return quizImageJpaRepository.save(QuizImageEntity.from(image)).toDomain()
    }

    override fun findById(id: QuizImageId): QuizImage? {
        return quizImageJpaRepository.findByIdOrNull(id.value)?.toDomain()
    }

    override fun deleteByIdIn(ids: List<QuizImageId>) {
        quizImageJpaRepository.deleteByIdIn(ids.map { it.value })
    }
}