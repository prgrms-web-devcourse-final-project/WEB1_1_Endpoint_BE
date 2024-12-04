package com.grepp.quizy.matching.infra.user.messaging.listener.quiz

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.grepp.quizy.common.NoArg
import com.grepp.quizy.matching.domain.user.InterestCategory
import com.grepp.quizy.matching.infra.user.entity.MatchingUserEntity
import com.grepp.quizy.matching.infra.user.entity.MatchingUserInterestVO
import com.grepp.quizy.matching.infra.user.messaging.listener.Event
import com.grepp.quizy.matching.infra.user.messaging.listener.EventType


data class QuizEvent(
    @JsonProperty("payload")
    override val payload: Map<String, Any>,
    @JsonProperty("event_type")
    override val eventType: QuizEventType,
    @JsonProperty("origin")
    override val origin: String
) : Event {

    enum class QuizEventType(
        @JsonValue
        val value: String
    ) : EventType {
        USER_INTERESTS_INITIALIZED("UserInterestsInitializedEvent");

        override fun getType(): String {
            return value
        }
    }
}

@NoArg
class UserInterestInitializedEvent(
    val userId: Long,
    val interests: List<String>
) {

    fun MatchingUserEntity() =
        MatchingUserEntity(
            id = userId,
            interests = interests.map {
                MatchingUserInterestVO(InterestCategory.valueOf(it))
            }.toMutableList()
        )
}