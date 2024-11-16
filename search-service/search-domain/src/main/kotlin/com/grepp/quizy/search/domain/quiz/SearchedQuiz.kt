package com.grepp.quizy.search.domain.quiz

data class SearchedQuiz(
    val id: Long,
    val question: String,
    val type: String,
    val choices: List<String>,
    val answer: String,
    val explanation: String,
    val likesCount: Int,
    val commentCount: Int,
    val isLiked: Boolean
) {

    companion object {
        fun of(quiz: Quiz, isLiked: Boolean) =
            SearchedQuiz(
                id = quiz.id,
                question = quiz.question,
                type = quiz.getTypeName(),
                choices = quiz.getChoices(),
                answer = quiz.getAnswer(),
                explanation = quiz.getExplanation(),
                likesCount = quiz.getNumLikes(),
                commentCount = quiz.getNumComments(),
                isLiked = isLiked,
            )
    }
}
