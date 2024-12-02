package com.grepp.quizy.quiz.infra.useranswer.entity

import com.grepp.quizy.jpa.BaseTimeEntity
import com.grepp.quizy.quiz.domain.useranswer.Choice
import com.grepp.quizy.quiz.domain.useranswer.ReviewStatus
import com.grepp.quizy.quiz.domain.useranswer.UserAnswer
import jakarta.persistence.*

@Entity
@Table(name = "user_answers")
class UserAnswerEntity(
        @EmbeddedId val id: UserAnswerEntityId,
        val choice: Int,
        @Enumerated(EnumType.STRING)
        val reviewStatus: ReviewStatus = ReviewStatus.NOT_REVIEWED,
        val isCorrect: Boolean? = null,
) : BaseTimeEntity() {

    fun toDomain(): UserAnswer {
        val domainChoice =
                when (isCorrect != null) {
                    true -> Choice.create(choice, isCorrect)
                    false -> Choice.create(choice)
                }

        return UserAnswer(
            key = id.toDomain(),
            choice = domainChoice,
            _reviewStatus = reviewStatus,
            answeredAt = createdAt
        )
    }

    companion object {
        fun from(domain: UserAnswer): UserAnswerEntity {
            return when (val choice = domain.choice) {
                is Choice.AnswerableChoice ->
                    UserAnswerEntity(
                        id = UserAnswerEntityId.from(domain.key),
                        choice = choice.choiceNumber,
                        reviewStatus = domain.reviewStatus,
                        isCorrect = choice.isCorrect,
                    )

                is Choice.NonAnswerableChoice ->
                    UserAnswerEntity(
                        id = UserAnswerEntityId.from(domain.key),
                        choice = choice.choiceNumber,
                        reviewStatus = domain.reviewStatus,
                    )
            }.also {
                it.createdAt = domain.answeredAt
            }
        }
    }
}
