package com.grepp.quizy.quiz.infra.quizread.messaging.listener.cdc

import com.fasterxml.jackson.annotation.JsonProperty

data class QuizTagMappingCDCEvent(
    @JsonProperty("quiz_id") val quizId: Long,
    @JsonProperty("tag_id") val tagId: Long
) {
}