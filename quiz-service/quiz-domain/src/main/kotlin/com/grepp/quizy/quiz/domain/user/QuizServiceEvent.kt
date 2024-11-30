package com.grepp.quizy.quiz.domain.user

import com.grepp.quizy.common.event.ServiceEvent
import java.time.LocalDateTime

interface QuizServiceEvent : ServiceEvent {
    override val eventType: String
    override val aggregateType: String
    override val aggregateId: Long
    override val timestamp: LocalDateTime
    override fun toPayload(): Map<String, Any>
    override fun getOrigin(): String {
        return "quiz-service"
    }
}