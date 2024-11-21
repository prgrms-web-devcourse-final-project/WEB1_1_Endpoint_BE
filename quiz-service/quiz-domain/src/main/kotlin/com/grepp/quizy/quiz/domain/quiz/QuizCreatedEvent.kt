package com.grepp.quizy.quiz.domain.quiz

import java.time.LocalDateTime

class QuizCreatedEvent(
        private val quizId: Long,
        val type: String,
        val category: QuizCategory,
        val content: String,
        val tags: List<String>,
        val options: List<QuizOption>,
        val answer: String?,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
) : QuizEvent {

    override fun getQuizId(): Long {
        return quizId
    }

    companion object {
        fun from(quiz: Quiz): QuizCreatedEvent {
            return when (quiz) {
                is Answerable ->
                        QuizCreatedEvent(
                                quiz.id.value,
                                quiz.type.name,
                                quiz.content.category,
                                quiz.content.content,
                                quiz.content.tags.map { it.name },
                                quiz.content.options,
                                quiz.getCorrectAnswer(),
                                quiz.dateTime.createdAt!!,
                                quiz.dateTime.updatedAt!!,
                        )

                else ->
                        QuizCreatedEvent(
                                quiz.id.value,
                                quiz.type.name,
                                quiz.content.category,
                                quiz.content.content,
                                quiz.content.tags.map { it.name },
                                quiz.content.options,
                                null,
                                quiz.dateTime.createdAt!!,
                                quiz.dateTime.updatedAt!!,
                        )
            }
        }
    }
}
