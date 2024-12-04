package com.grepp.quizy.quiz.domain.user.events

import com.grepp.quizy.quiz.domain.global.event.QuizServiceEvent
import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.user.QuizUser
import java.time.LocalDateTime

data class UserInterestsInitializedEvent(
    val userId: Long,
    val interests: List<QuizCategory>
) : QuizServiceEvent {
    override val eventType: String = javaClass.simpleName
    override val aggregateType: String = "QuizUser"
    override val aggregateId: Long = userId
    override val timestamp: LocalDateTime = LocalDateTime.now()

    override fun toPayload(): Map<String, Any> {
        return mapOf(
            "userId" to userId,
            "interests" to interests
        )
    }

    companion object {
        fun from(user: QuizUser): UserInterestsInitializedEvent {
            return UserInterestsInitializedEvent(
                userId = user.id.value,
                interests = user.interests
            )
        }
    }

}