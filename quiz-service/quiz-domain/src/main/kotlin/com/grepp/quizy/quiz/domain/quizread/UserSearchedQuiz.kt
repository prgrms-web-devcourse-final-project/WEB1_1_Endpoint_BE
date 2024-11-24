package com.grepp.quizy.quiz.domain.quizread

import kotlin.math.round

sealed interface QuizWithDetail

sealed interface NotAnswerableQuizDetail : QuizWithDetail

sealed interface AnswerableQuizDetail : QuizWithDetail

data class NotAnsweredQuizWithoutAnswer(
    val id: Long,
    val content: String,
    val type: String,
    val options: List<QuizDetailOption>,
    val count: QuizDetailCount,
    val isLiked: Boolean,
) : QuizWithDetail, NotAnswerableQuizDetail {

    companion object {
        fun from(quiz: QuizForRead, isLiked: Boolean): NotAnsweredQuizWithoutAnswer =
                with(quiz) {
                    val totalSelection =
                            options.sumOf { it.selectionCount }
                    return NotAnsweredQuizWithoutAnswer(
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

data class AnsweredQuizWithoutAnswer(
    val id: Long,
    val content: String,
    val type: String,
    val options: List<QuizDetailOption>,
    val answeredOption: String,
    val count: QuizDetailCount,
    val isLiked: Boolean,
) : QuizWithDetail, NotAnswerableQuizDetail {

    companion object {
        fun from(
            quiz: QuizForRead,
            answeredOption: String,
            isLiked: Boolean
        ): AnsweredQuizWithoutAnswer =
            with(quiz) {
                val totalSelection =
                    options.sumOf { it.selectionCount }
                return AnsweredQuizWithoutAnswer(
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
                    answeredOption = answeredOption,
                    isLiked = isLiked,
                )
            }
    }
}

data class NotAnsweredQuizWithAnswer(
    val id: Long,
    val content: String,
    val type: String,
    val options: List<QuizDetailOption>,
    val answer: QuizDetailAnswer,
    val count: QuizDetailCount,
    val isLiked: Boolean,
) : QuizWithDetail, AnswerableQuizDetail {

    companion object {
        fun from(
                quiz: AnswerableQuiz,
                isLiked: Boolean,
        ): NotAnsweredQuizWithAnswer =
                with(quiz) {
                    val totalSelection =
                            options.sumOf { it.selectionCount }
                    return NotAnsweredQuizWithAnswer(
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

data class AnsweredQuizWithAnswer(
    val id: Long,
    val content: String,
    val type: String,
    val options: List<QuizDetailOption>,
    val answer: QuizDetailAnswer,
    val answeredOption: String,
    val count: QuizDetailCount,
    val isLiked: Boolean,
) : QuizWithDetail, AnswerableQuizDetail {

    companion object {
        fun from(
                quiz: AnswerableQuiz,
                answeredOption: String,
                isLiked: Boolean,
        ): AnsweredQuizWithAnswer =
                with(quiz) {
                    val totalSelection =
                            options.sumOf { it.selectionCount }
                    return AnsweredQuizWithAnswer(
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
