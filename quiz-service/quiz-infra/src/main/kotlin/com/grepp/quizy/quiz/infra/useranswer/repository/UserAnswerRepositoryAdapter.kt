package com.grepp.quizy.quiz.infra.useranswer.repository

import com.grepp.quizy.common.dto.Cursor
import com.grepp.quizy.common.dto.SliceResult
import com.grepp.quizy.jpa.utils.PagingUtil
import com.grepp.quizy.quiz.domain.quiz.QuizId
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

    override fun findAllByUserAnswerId(userAnswerKeys: List<UserAnswerKey>): UserAnswerPackage {
        val userAnswers = userAnswerJpaRepository
            .findAllById(userAnswerKeys.map { UserAnswerEntityId.from(it) })
            .associate { entity ->
                QuizId(entity.id.quizId) to when (entity.isCorrect) {
                    null -> Choice.create(entity.choice)
                    else -> Choice.create(entity.choice, entity.isCorrect)
                }
        }
        return UserAnswerPackage(userAnswers)
    }

    override fun findAllByUserId(userId: UserId): List<QuizId> =
        userAnswerJpaRepository.findAllQuizIdByUserId(userId.value).map { QuizId(it) }

    override fun findAllByUserIdAndIsCorrect(userId: UserId, isCorrect: Boolean, cursor: Cursor): SliceResult<UserAnswer> {
        val userAnswers = userAnswerJpaRepository
            .findAllByUserIdAndIsCorrect(userId.value, isCorrect, PagingUtil.toPageRequest(cursor))
            .map { it.toDomain() }
        return SliceResult.of(userAnswers.toList(), userAnswers.hasNext())
    }
}
