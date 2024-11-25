package com.grepp.quizy.quiz.domain.quizread

interface QuizFetcher {
    fun fetchUserLikeStatus(quizIds: List<QuizId>): QuizLikeStatus
}

data class QuizLikeStatus(val status: Map<QuizId, Boolean>) {
    fun isLikedBy(quizId: QuizId) = status[quizId] ?: false
}
