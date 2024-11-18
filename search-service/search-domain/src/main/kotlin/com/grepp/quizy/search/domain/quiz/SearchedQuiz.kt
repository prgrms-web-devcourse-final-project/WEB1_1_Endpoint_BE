package com.grepp.quizy.search.domain.quiz

sealed interface SearchedQuiz

sealed interface AnswerableQuiz

data class NonAnswerableQuiz(
    val id: Long,
    val content: String,
    val type: String,
    val options: List<SearchedQuizOption>,
    val count: SearchedQuizCount,
) : SearchedQuiz {

    companion object {
        fun from(quiz: Quiz, count: SearchedQuizCount): NonAnswerableQuiz = with(quiz) {
            val totalSelection = options.sumOf { it.selectionCount }
            return NonAnswerableQuiz(
                id = id(),
                content = content(),
                type = typeName(),
                options = options.map { SearchedQuizOption.from(it, totalSelection) },
                count = count,
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
) : SearchedQuiz, AnswerableQuiz {

    companion object {
        fun <T> from(quiz: T, count: SearchedQuizCount): UserNotAnsweredQuiz where T : Quiz, T : Answerable = with(quiz) {
            val totalSelection = options.sumOf { it.selectionCount }
            return UserNotAnsweredQuiz(
                id = id(),
                content = content(),
                type = typeName(),
                options = options.map { SearchedQuizOption.from(it, totalSelection) },
                answer = SearchedQuizAnswer(answer(), explanation()),
                count = count,
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
) : SearchedQuiz, AnswerableQuiz {

    companion object {
        fun <T> from(quiz: T, answeredOption: Int, count: SearchedQuizCount): UserAnsweredQuiz where T : Quiz, T : Answerable = with(quiz) {
            val totalSelection = options.sumOf { it.selectionCount }
            return UserAnsweredQuiz(
                id = id(),
                content = content(),
                type = typeName(),
                options = options.map { SearchedQuizOption.from(it, totalSelection) },
                answer = SearchedQuizAnswer(answer(), explanation()),
                answeredOption = answeredOption,
                count = count,
            )
        }
    }
}

data class SearchedQuizCount(val like: Int, val comment: Int)

data class SearchedQuizOption(val no: Int, val content: String, val selectionRatio: Double) {

    companion object {
        fun from(option: QuizOption, total: Int) =
            SearchedQuizOption(
                option.optionNumber,
                option.content,
                (option.selectionCount / total).toDouble(),
            )
    }
}

data class SearchedQuizAnswer(val content: String, val explanation: String)
