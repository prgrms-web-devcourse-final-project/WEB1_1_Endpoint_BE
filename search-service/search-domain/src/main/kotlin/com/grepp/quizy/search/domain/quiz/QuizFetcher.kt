package com.grepp.quizy.search.domain.quiz

interface QuizFetcher {

    fun fetchUserLikeStatus(quizIds: List<QuizId>): QuizLikeStatus
}

data class QuizLikeStatus(val status: Map<QuizId, Boolean>) {
    fun isLikedBy(quizId: QuizId) = status[quizId] ?: false
}
