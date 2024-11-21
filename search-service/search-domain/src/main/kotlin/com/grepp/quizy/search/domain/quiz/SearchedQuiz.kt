package com.grepp.quizy.search.domain.quiz

import kotlin.math.round

sealed interface SearchedQuiz

sealed interface AnswerableQuiz : SearchedQuiz

data class NonAnswerableQuiz(
    val id: Long,
    val content: String,
    val type: String,
    val options: List<SearchedQuizOption>,
    val count: SearchedQuizCount,
    val isLiked: Boolean,
) : SearchedQuiz {

    companion object {
        fun from(quiz: Quiz, isLiked: Boolean): NonAnswerableQuiz = with(quiz) {
            val totalSelection = options.sumOf { it.selectionCount }
            return NonAnswerableQuiz(
                id = id(),
                content = content(),
                type = typeName(),
                options = options.map { SearchedQuizOption.from(it, totalSelection) },
                count = SearchedQuizCount.from(count),
                isLiked = isLiked
            )
        }
    }
}

data class UserNotAnsweredQuiz(
    val id: Long,
    val content: String,
    val type: String,
    val options: List<SearchedQuizOption>,
    val answer: SearchedQuizAnswer,
    val count: SearchedQuizCount,
    val isLiked: Boolean,
) : SearchedQuiz, AnswerableQuiz {

    companion object {
        fun <T> from(
            quiz: T,
            isLiked: Boolean
        ): UserNotAnsweredQuiz where T : Quiz, T : Answerable = with(quiz) {
            val totalSelection = options.sumOf { it.selectionCount }
            return UserNotAnsweredQuiz(
                id = id(),
                content = content(),
                type = typeName(),
                options = options.map { SearchedQuizOption.from(it, totalSelection) },
                answer = SearchedQuizAnswer(answer(), explanation()),
                count = SearchedQuizCount.from(count),
                isLiked = isLiked
            )
        }
    }
}

data class UserAnsweredQuiz(
    val id: Long,
    val content: String,
    val type: String,
    val options: List<SearchedQuizOption>,
    val answer: SearchedQuizAnswer,
    val answeredOption: Int,
    val count: SearchedQuizCount,
    val isLiked: Boolean,
) : SearchedQuiz, AnswerableQuiz {

    companion object {
        fun <T> from(
            quiz: T,
            answeredOption: Int,
            isLiked: Boolean
        ): UserAnsweredQuiz where T : Quiz, T : Answerable = with(quiz) {
            val totalSelection = options.sumOf { it.selectionCount }
            return UserAnsweredQuiz(
                id = id(),
                content = content(),
                type = typeName(),
                options = options.map { SearchedQuizOption.from(it, totalSelection) },
                answer = SearchedQuizAnswer(answer(), explanation()),
                answeredOption = answeredOption,
                count = SearchedQuizCount.from(count),
                isLiked = isLiked
            )
        }
    }
}

data class SearchedQuizCount(val like: Int = 0, val comment: Int = 0) {

    companion object {
        fun from(count: QuizCount?) = count?.let {
            SearchedQuizCount(it.like, it.comment)
        } ?: SearchedQuizCount()
    }
}

data class SearchedQuizOption(val no: Int, val content: String, val selectionRatio: Double) {

    companion object {
        fun from(option: QuizOption, total: Int) =
            SearchedQuizOption(
                option.optionNumber,
                option.content,
                roundUp(option.selectionCount.toDouble() / total.toDouble())
            )

        private fun roundUp(value: Double) = round(value * 100) / 100.0
    }
}

data class SearchedQuizAnswer(val content: String, val explanation: String)
