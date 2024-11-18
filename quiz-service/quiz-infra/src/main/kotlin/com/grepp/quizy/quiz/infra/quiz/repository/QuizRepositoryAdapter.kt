package com.grepp.quizy.quiz.infra.quiz.repository

import com.grepp.quizy.quiz.domain.quiz.*
import com.grepp.quizy.quiz.infra.quiz.entity.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class QuizRepositoryAdapter(
        private val quizJpaRepository: QuizJpaRepository,
        private val quizTagJpaRepository:
                QuizTagJpaRepository,
) : QuizRepository {
    override fun save(quiz: Quiz): Quiz {
        val quizEntity =
                when (quiz) {
                    is ABTest -> ABTestEntity.from(quiz)
                    is OXQuiz -> OXQuizEntity.from(quiz)
                    is MultipleChoiceQuiz ->
                            MultipleChoiceQuizEntity.from(
                                    quiz
                            )
                    else ->
                            throw IllegalArgumentException(
                                    "알 수 없는 퀴즈 타입입니다"
                            )
                }
        return quizJpaRepository.save(quizEntity).toDomain()
    }

    override fun update(quiz: Quiz): Quiz {
        val quizEntity =
                quizJpaRepository
                        .findById(quiz.id.value)
                        .orElseThrow()
        return quizEntity.update(quiz).toDomain()
    }

    override fun findById(id: QuizId): Quiz? {
        return quizJpaRepository
                .findById(id.value)
                .orElseThrow {
                    IllegalArgumentException(
                            "해당 ID의 퀴즈가 존재하지 않습니다"
                    )
                }
                .toDomain()
    }

    override fun findTagsByNameIn(
            name: List<String>
    ): List<QuizTag> {
        return quizTagJpaRepository.findByNameIn(name).map {
            it.toDomain()
        }
    }

    override fun saveTags(
            newTags: List<QuizTag>
    ): List<QuizTag> {
        return quizTagJpaRepository
                .saveAll(
                        newTags.map {
                            QuizTagEntity.from(it)
                        }
                )
                .map { it.toDomain() }
    }

    override fun delete(quiz: Quiz) {
        quizJpaRepository.delete(QuizEntity.from(quiz))
    }
}
