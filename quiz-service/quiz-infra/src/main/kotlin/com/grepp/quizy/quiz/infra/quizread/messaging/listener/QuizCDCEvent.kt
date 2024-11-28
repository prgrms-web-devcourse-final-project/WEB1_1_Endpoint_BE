package com.grepp.quizy.quiz.infra.quizread.messaging.listener

import com.fasterxml.jackson.annotation.JsonProperty
import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.quiz.QuizType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class QuizCDCEvent(
    @JsonProperty("quiz_id") val id: Long,
    @JsonProperty("user_id") val userId: Long,
    @JsonProperty("type") val type: QuizType,
    @JsonProperty("category") val category: QuizCategory,
    @JsonProperty("content") val content: String,
    @JsonProperty("answer") val answer: String?,
    @JsonProperty("explanation") val explanation: String?,
    @JsonProperty("comment_count") val commentCount: Int,
    @JsonProperty("like_count") val likeCount: Int,
    @JsonProperty("created_at") val createdAt: Long,
    @JsonProperty("updated_at") val updatedAt: Long
) {
    fun getCreatedAt(): LocalDateTime {
        return LocalDateTime.ofInstant(
            Instant.ofEpochMilli(createdAt / 1000),
            ZoneId.systemDefault()
        )
    }

    fun getUpdatedAt(): LocalDateTime {
        return LocalDateTime.ofInstant(
            Instant.ofEpochMilli(updatedAt / 1000),
            ZoneId.systemDefault()
        )
    }
}