package com.grepp.quizy.quiz.infra.useranswer.repository

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.quiz.QuizType
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.domain.useranswer.*
import com.grepp.quizy.quiz.infra.useranswer.entity.UserAnswerEntity
import com.grepp.quizy.quiz.infra.useranswer.entity.UserAnswerEntityId
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

    override fun findAllByUserAnswerId(userAnswerIds: List<UserAnswerId>): UserAnswerPackage {
        val userAnswers = userAnswerJpaRepository
            .findAllById(userAnswerIds.map { UserAnswerEntityId.from(it) })
            .associate { entity ->
                QuizId(entity.id.quizId) to when (entity.quizType) {
                    QuizType.AB_TEST -> Choice.create(entity.quizType, entity.choice)
                    QuizType.OX, QuizType.MULTIPLE_CHOICE ->
                        Choice.create(entity.quizType, entity.choice, entity.isCorrect!!)
                }
        }

        return UserAnswerPackage(userAnswers)
    }

    override fun findAllByUserId(userId: UserId): List<QuizId> =
        userAnswerJpaRepository.findAllQuizIdByUserId(userId.value).map { QuizId(it) }
}
