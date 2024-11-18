package com.grepp.quizy.search.domain.quiz

sealed class Quiz(
    private val id: QuizId,
    private val content: QuizContent,
    val category: QuizCategory,
    val tags: List<QuizTag>,
    val options: List<QuizOption>,
) {
    fun id() = id.value

    fun typeName() = content.type.typeName

    fun content() = content.value
}
