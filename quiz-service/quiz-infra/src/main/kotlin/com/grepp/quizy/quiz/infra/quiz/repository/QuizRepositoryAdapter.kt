package com.grepp.quizy.quiz.infra.quiz.repository

import com.grepp.quizy.quiz.domain.quiz.*
import com.grepp.quizy.quiz.infra.quiz.entity.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class QuizRepositoryAdapter(
        private val quizJpaRepository: QuizJpaRepository,
        private val quizTagJpaRepository: QuizTagJpaRepository,
) : QuizRepository {
    override fun save(quiz: Quiz): Quiz {
        return quizJpaRepository
                .save(QuizEntity.from(quiz))
                .toDomain()
    }

    override fun findById(id: QuizId): Quiz? {
        return quizJpaRepository
                .findById(id.value)
                .map { it.toDomain() }
                .orElse(null)
    }

    override fun findByIdWithLock(id: QuizId): Quiz? {
        return quizJpaRepository
                .findByIdWithLock(id.value)
                .map { it.toDomain() }
                .orElse(null)
    }

    override fun findTagsByInId(ids: List<QuizTagId>): List<QuizTag> {
        return quizTagJpaRepository
                .findAllById(ids.map { it.value })
                .map { it.toDomain() }
    }

    override fun findCountsByInId(ids: List<QuizId>): QuizCountPackage {
        val quizCounts = quizJpaRepository
            .findAllById(ids.map { it.value })
            .associate { QuizId(it.quizId) to QuizCount(it.likeCount, it.commentCount) }
        return QuizCountPackage(quizCounts)
    }

    override fun findTagsByNameIn(
            names: List<String>
    ): List<QuizTag> {
        return quizTagJpaRepository.findByNameIn(names).map {
            it.toDomain()
        }
    }

    override fun existsUserAnswerByQuizId(quizId: QuizId): Boolean {
        return quizJpaRepository.existsUserAnswerByQuizId(
                quizId.value
        )
    }

    override fun saveTags(newTags: List<QuizTag>): List<QuizTag> {
        return quizTagJpaRepository
                .saveAll(newTags.map { QuizTagEntity.from(it) })
                .map { it.toDomain() }
    }

    override fun delete(quiz: Quiz) {
        quizJpaRepository.delete(QuizEntity.from(quiz))
    }
}
