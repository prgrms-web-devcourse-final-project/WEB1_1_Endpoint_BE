package com.grepp.quizy.quiz.infra.quizread.messaging.listener

import com.fasterxml.jackson.annotation.JsonProperty

data class QuizOptionCDCEvent(
    @JsonProperty("quiz_id") val quizId: Long,
    @JsonProperty("option_number") val optionNumber: Int,
    @JsonProperty("content") val content: String,
    @JsonProperty("selection_count") val selectionCount: Int,
    @JsonProperty("image_id") val imageId: Long? = null
) {
}