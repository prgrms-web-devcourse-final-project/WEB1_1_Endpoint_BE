package com.grepp.quizy.quiz.infra.like.entity

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
class LikeEntityId(val userId: Long = 0, val quizId: Long = 0) :
        Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LikeEntityId) return false

        return userId == other.userId && quizId == other.quizId
    }

    override fun hashCode(): Int {
        return Objects.hash(userId, quizId)
    }
}
