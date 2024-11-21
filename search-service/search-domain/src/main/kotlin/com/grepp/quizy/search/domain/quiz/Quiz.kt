package com.grepp.quizy.search.domain.quiz

<<<<<<< Updated upstream
sealed class Quiz(
    val id: QuizId,
    private val content: QuizContent,
    val category: QuizCategory,
    val tags: List<QuizTag>,
    val options: List<QuizOption>,
    val count: QuizCount,
=======
class Quiz(
        val id: Long,
        val question: String,
        private val type: QuizType,
        private val tag: QuizTag,
        private val choice: QuizChoice,
        private val like: QuizLikeCount,
        private val comment: QuizCommentCount,
>>>>>>> Stashed changes
) {
    fun id() = id.value

    fun typeName() = content.type.typeName

    fun content() = content.value
}
