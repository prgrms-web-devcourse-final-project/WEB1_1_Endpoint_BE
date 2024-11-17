package com.grepp.quizy.quiz.infra.quiz.repository

import com.grepp.quizy.quiz.domain.*
import com.grepp.quizy.quiz.infra.quiz.entity.ABTestEntity
import com.grepp.quizy.quiz.infra.quiz.entity.MultipleChoiceQuizEntity
import com.grepp.quizy.quiz.infra.quiz.entity.OXQuizEntity
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

    override fun findTagsByNameIn(
            name: List<String>
    ): List<QuizTag> {
        return quizTagJpaRepository.findByNameIn(name).map {
            it.toDomain()
        }
    }
}
