package com.grepp.quizy.quiz.infra.useranswer.entity

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerId
import com.grepp.quizy.quiz.domain.useranswer.UserId
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
data class UserAnswerEntityId(
        val userId: Long = 0L,
        val quizId: Long = 0L,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserAnswerEntityId) return false

        return userId == other.userId &&
                quizId == other.quizId
    }

    override fun hashCode(): Int {
        return Objects.hash(userId, quizId)
    }

    fun toDomain(): UserAnswerId {
        return UserAnswerId(
                userId = UserId(userId),
                quizId = QuizId(quizId),
        )
    }

    companion object {
        fun from(domain: UserAnswerId): UserAnswerEntityId {
            return UserAnswerEntityId(
                    userId = domain.userId.value,
                    quizId = domain.quizId.value,
            )
        }
    }
}
