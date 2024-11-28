package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.common.NoArg
import java.time.LocalDateTime

@NoArg
class QuizCreatedEvent(
        private val quizId: Long,
        val creatorId: Long,
        val type: QuizType,
        val category: QuizCategory,
        val content: String,
        val tags: List<QuizTag>,
        val options: List<QuizOption>,
        val answer: QuizAnswer?,
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
                                quiz.creatorId.value,
                                quiz.type,
                                quiz.content.category,
                                quiz.content.content,
                                quiz.content.tags,
                                quiz.content.options,
                                quiz.getQuizAnswer(),
                                quiz.dateTime.createdAt!!,
                                quiz.dateTime.updatedAt!!,
                        )

                else ->
                        QuizCreatedEvent(
                                quiz.id.value,
                                quiz.creatorId.value,
                                quiz.type,
                                quiz.content.category,
                                quiz.content.content,
                                quiz.content.tags,
                                quiz.content.options,
                                null,
                                quiz.dateTime.createdAt!!,
                                quiz.dateTime.updatedAt!!,
                        )
            }
        }
    }
}
