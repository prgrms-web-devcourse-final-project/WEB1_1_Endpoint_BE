package com.grepp.quizy.search.domain.quiz

import kotlin.math.round

sealed interface QuizWithDetail

sealed interface AnswerableQuiz : QuizWithDetail

data class NonAnswerableQuizWithDetail(
    val id: Long,
    val content: String,
    val type: String,
    val options: List<QuizDetailOption>,
    val count: QuizDetailCount,
    val isLiked: Boolean,
) : QuizWithDetail {

    companion object {
        fun from(quiz: Quiz, isLiked: Boolean): NonAnswerableQuizWithDetail =
                with(quiz) {
                    val totalSelection =
                            options.sumOf { it.selectionCount }
                    return NonAnswerableQuizWithDetail(
                            id = id(),
                            content = content(),
                            type = typeName(),
                            options =
                                    options.map {
                                        QuizDetailOption.from(
                                                it,
                                                totalSelection,
                                        )
                                    },
                            count = QuizDetailCount.from(count),
                            isLiked = isLiked,
                    )
                }
    }
}

data class UserNotAnsweredQuiz(
    val id: Long,
    val content: String,
    val type: String,
    val options: List<QuizDetailOption>,
    val answer: QuizDetailAnswer,
    val count: QuizDetailCount,
    val isLiked: Boolean,
) : QuizWithDetail, AnswerableQuiz {

    companion object {
        fun <T> from(
                quiz: T,
                isLiked: Boolean,
        ): UserNotAnsweredQuiz where T : Quiz, T : Answerable =
                with(quiz) {
                    val totalSelection =
                            options.sumOf { it.selectionCount }
                    return UserNotAnsweredQuiz(
                            id = id(),
                            content = content(),
                            type = typeName(),
                            options =
                                    options.map {
                                        QuizDetailOption.from(
                                                it,
                                                totalSelection,
                                        )
                                    },
                            answer =
                                    QuizDetailAnswer(
                                            answer(),
                                            explanation(),
                                    ),
                            count = QuizDetailCount.from(count),
                            isLiked = isLiked,
                    )
                }
    }
}

data class UserAnsweredQuiz(
    val id: Long,
    val content: String,
    val type: String,
    val options: List<QuizDetailOption>,
    val answer: QuizDetailAnswer,
    val answeredOption: Int,
    val count: QuizDetailCount,
    val isLiked: Boolean,
) : QuizWithDetail, AnswerableQuiz {

    companion object {
        fun <T> from(
                quiz: T,
                answeredOption: Int,
                isLiked: Boolean,
        ): UserAnsweredQuiz where T : Quiz, T : Answerable =
                with(quiz) {
                    val totalSelection =
                            options.sumOf { it.selectionCount }
                    return UserAnsweredQuiz(
                            id = id(),
                            content = content(),
                            type = typeName(),
                            options =
                                    options.map {
                                        QuizDetailOption.from(
                                                it,
                                                totalSelection,
                                        )
                                    },
                            answer =
                                    QuizDetailAnswer(
                                            answer(),
                                            explanation(),
                                    ),
                            answeredOption = answeredOption,
                            count = QuizDetailCount.from(count),
                            isLiked = isLiked,
                    )
                }
    }
}

data class QuizDetailCount(
        val like: Int = 0,
        val comment: Int = 0,
) {

    companion object {
        fun from(count: QuizCount?) =
                count?.let { QuizDetailCount(it.like, it.comment) }
                        ?: QuizDetailCount()
    }
}

data class QuizDetailOption(
        val no: Int,
        val content: String,
        val selectionRatio: Double,
) {

    companion object {
        fun from(option: QuizOption, total: Int) =
                QuizDetailOption(
                        option.optionNumber,
                        option.content,
                        roundUp(
                                option.selectionCount.toDouble() /
                                        total.toDouble()
                        ),
                )

        private fun roundUp(value: Double) =
                round(value * 100) / 100.0
    }
}

data class QuizDetailAnswer(
        val content: String,
        val explanation: String,
)
