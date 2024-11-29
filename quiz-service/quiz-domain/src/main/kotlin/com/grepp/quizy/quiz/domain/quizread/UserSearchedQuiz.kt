package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.image.QuizImageId
import com.grepp.quizy.quiz.domain.quiz.Answerable
import com.grepp.quizy.quiz.domain.quiz.Quiz
import com.grepp.quizy.quiz.domain.quiz.QuizCount
import com.grepp.quizy.quiz.domain.quiz.QuizOption
import kotlin.math.round

sealed interface QuizWithDetail

sealed interface NotAnswerableQuizDetail : QuizWithDetail

sealed interface AnswerableQuizDetail : QuizWithDetail

data class NotAnsweredQuizWithoutAnswer(
    val id: Long,
    val author: QuizAuthor?,
    val content: String,
    val type: String,
    val options: List<QuizDetailOption>,
    val count: QuizDetailCount,
    val isLiked: Boolean,
) : QuizWithDetail, NotAnswerableQuizDetail {

    companion object {
        fun from(
            quiz: Quiz,
            author: QuizAuthor?,
            count: QuizCount,
            isLiked: Boolean,
            optionImages: Map<QuizImageId, String>
        ): NotAnsweredQuizWithoutAnswer =
                with(quiz) {
                    val totalSelection =
                            content.options.sumOf { it.selectionCount }
                    return NotAnsweredQuizWithoutAnswer(
                            id = id.value,
                            author = author,
                            content = content.content,
                            type = type.value,
                            options =
                                    content.options.map {
                                        QuizDetailOption.from(
                                            it,
                                            totalSelection,
                                            optionImages[(it as QuizOption.ABTestOption).imageId]
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
    val author: QuizAuthor?,
    val content: String,
    val type: String,
    val options: List<QuizDetailOption>,
    val answeredOption: String,
    val count: QuizDetailCount,
    val isLiked: Boolean,
) : QuizWithDetail, NotAnswerableQuizDetail {

    companion object {
        fun from(
            quiz: Quiz,
            author: QuizAuthor?,
            count: QuizCount,
            answeredOption: String,
            isLiked: Boolean,
            optionImages: Map<QuizImageId, String>
        ): AnsweredQuizWithoutAnswer =
            with(quiz) {
                val totalSelection =
                    content.options.sumOf { it.selectionCount }
                return AnsweredQuizWithoutAnswer(
                    id = id.value,
                    author = author,
                    content = content.content,
                    type = type.value,
                    options =
                        content.options.map {
                            QuizDetailOption.from(
                                it,
                                totalSelection,
                                optionImages[(it as QuizOption.ABTestOption).imageId]
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
    val author: QuizAuthor?,
    val content: String,
    val type: String,
    val options: List<QuizDetailOption>,
    val answer: QuizDetailAnswer,
    val count: QuizDetailCount,
    val isLiked: Boolean,
) : QuizWithDetail, AnswerableQuizDetail {

    companion object {
        fun <T> from(
                quiz: T,
                author: QuizAuthor?,
                count: QuizCount,
                isLiked: Boolean,
        ): NotAnsweredQuizWithAnswer where T : Quiz, T : Answerable =
                with(quiz) {
                    val totalSelection =
                            content.options.sumOf { it.selectionCount }
                    return NotAnsweredQuizWithAnswer(
                            id = id.value,
                            author = author,
                            content = content.content,
                            type = type.value,
                            options =
                                    content.options.map {
                                        QuizDetailOption.from(
                                                it,
                                                totalSelection,
                                        )
                                    },
                            answer =
                                    QuizDetailAnswer(
                                            getCorrectAnswer(),
                                            getAnswerExplanation(),
                                    ),
                            count = QuizDetailCount.from(count),
                            isLiked = isLiked,
                    )
                }
    }
}

data class AnsweredQuizWithAnswer(
    val id: Long,
    val author: QuizAuthor?,
    val content: String,
    val type: String,
    val options: List<QuizDetailOption>,
    val answer: QuizDetailAnswer,
    val answeredOption: String,
    val count: QuizDetailCount,
    val isLiked: Boolean,
) : QuizWithDetail, AnswerableQuizDetail {

    companion object {
        fun <T> from(
                quiz: T,
                author: QuizAuthor?,
                count: QuizCount,
                answeredOption: String,
                isLiked: Boolean,
        ): AnsweredQuizWithAnswer where T : Quiz, T : Answerable =
                with(quiz) {
                    val totalSelection =
                            content.options.sumOf { it.selectionCount }
                    return AnsweredQuizWithAnswer(
                            id = id.value,
                            author = author,
                            content = content.content,
                            type = type.value,
                            options =
                                    content.options.map {
                                        QuizDetailOption.from(
                                                it,
                                                totalSelection,
                                        )
                                    },
                            answer =
                                    QuizDetailAnswer(
                                            getCorrectAnswer(),
                                            getAnswerExplanation(),
                                    ),
                            answeredOption = answeredOption,
                            count = QuizDetailCount.from(count),
                            isLiked = isLiked,
                    )
                }
    }
}

data class QuizAuthor(val name: String, val imagePath: String)

data class QuizDetailCount(
        val like: Long = 0,
        val comment: Long = 0,
) {

    companion object {
        fun from(count: QuizCount) = QuizDetailCount(count.like, count.comment)
    }
}

data class QuizDetailOption(
        val no: Int,
        val content: String,
        val selectionRatio: Double,
        val imagePath: String?
) {

    companion object {
        fun from(option: QuizOption, total: Int, imagePath: String? = null) =
                QuizDetailOption(
                        option.optionNumber,
                        option.content,
                        roundUp(
                                option.selectionCount.toDouble() /
                                        total.toDouble()
                        ),
                        imagePath = imagePath
                )

        private fun roundUp(value: Double) =
                round(value * 100) / 100.0
    }
}

data class QuizDetailAnswer(
        val content: String,
        val explanation: String,
)
