package com.grepp.quizy.quiz.infra.quizread.messaging.listener.cdc

import com.fasterxml.jackson.annotation.JsonProperty

data class QuizCDCEvent(
    @JsonProperty("id") val id: Long,
    @JsonProperty("content") val content: String,
    @JsonProperty("answer") val answer: String?,
    @JsonProperty("explanation") val explanation: String?,
    @JsonProperty("updated_at") val updatedAt: Long
) {

}