package com.grepp.quizy.search.domain.quiz

interface QuizFetcher {
    fun getQuizAdditionalData(quizIds: List<QuizId>): QuizAdditionalPackage {
        val quizCount = fetchQuizCount(quizIds)
        val likeStatus = fetchUserLikeStatus(quizIds)
        return QuizAdditionalPackage.of(quizIds, quizCount, likeStatus)
    }

    fun fetchQuizCount(quizIds: List<QuizId>): QuizCountPackage

    fun fetchUserLikeStatus(quizIds: List<QuizId>): QuizLikeStatus
}

data class QuizAdditionalPackage(val value: Map<QuizId, SearchedQuizExtras>) {
    fun get(id: QuizId): SearchedQuizExtras = value[id] ?: SearchedQuizExtras()

    companion object {
        fun of(ids: List<QuizId>, quizCount: QuizCountPackage, likeStatus: QuizLikeStatus) =
            QuizAdditionalPackage(ids.associateWith { id ->
                SearchedQuizExtras(SearchedQuizCount.from(quizCount.getCountOf(id)), likeStatus.isLikedBy(id))
            })
    }
}

data class QuizCountPackage(val mappedCount: Map<QuizId, QuizCount>) {
    fun getCountOf(id: QuizId) = mappedCount[id]
}

data class QuizCount(val like: Int, val comment: Int)

data class QuizLikeStatus(val status: Map<QuizId, Boolean>) {
    fun isLikedBy(quizId: QuizId) = status[quizId] ?: false
}
