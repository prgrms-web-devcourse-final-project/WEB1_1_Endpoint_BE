package com.grepp.quizy.quiz.domain.quizread

sealed class QuizForRead(
        val id: QuizId,
        private val content: QuizContent,
        val category: QuizCategory,
        val tags: List<QuizTag>,
        val options: List<QuizOption>,
) {
    fun id() = id.value

    fun typeName() = content.type.typeName

    fun content() = content.value
}
