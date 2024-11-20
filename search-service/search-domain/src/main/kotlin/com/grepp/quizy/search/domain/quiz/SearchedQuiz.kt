package com.grepp.quizy.search.domain.quiz

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
        fun from(quiz: Quiz, quizExtras: SearchedQuizExtras): NonAnswerableQuiz = with(quiz) {
            val totalSelection = options.sumOf { it.selectionCount }
            return NonAnswerableQuiz(
                id = id(),
                content = content(),
                type = typeName(),
                options = options.map { SearchedQuizOption.from(it, totalSelection) },
                count = quizExtras.count,
                isLiked = quizExtras.isLiked
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
            quizExtras: SearchedQuizExtras
        ): UserNotAnsweredQuiz where T : Quiz, T : Answerable = with(quiz) {
            val totalSelection = options.sumOf { it.selectionCount }
            return UserNotAnsweredQuiz(
                id = id(),
                content = content(),
                type = typeName(),
                options = options.map { SearchedQuizOption.from(it, totalSelection) },
                answer = SearchedQuizAnswer(answer(), explanation()),
                count = quizExtras.count,
                isLiked = quizExtras.isLiked
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
            extras: SearchedQuizExtras
        ): UserAnsweredQuiz where T : Quiz, T : Answerable = with(quiz) {
            val totalSelection = options.sumOf { it.selectionCount }
            return UserAnsweredQuiz(
                id = id(),
                content = content(),
                type = typeName(),
                options = options.map { SearchedQuizOption.from(it, totalSelection) },
                answer = SearchedQuizAnswer(answer(), explanation()),
                answeredOption = answeredOption,
                count = extras.count,
                isLiked = extras.isLiked
            )
        }
    }
}

data class SearchedQuizExtras(val count: SearchedQuizCount = SearchedQuizCount(), val isLiked: Boolean = false)

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
                option.selectionCount.toDouble() / total.toDouble()
            )
    }
}

data class SearchedQuizAnswer(val content: String, val explanation: String)
