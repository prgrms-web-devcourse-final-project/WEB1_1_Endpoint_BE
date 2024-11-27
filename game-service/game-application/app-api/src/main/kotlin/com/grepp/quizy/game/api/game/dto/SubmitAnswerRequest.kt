package com.grepp.quizy.game.api.game.dto

data class SubmitAnswerRequest(
    val quizId: Long,
    val answer: String,
    val submissionTimestamp: Long
) {

}