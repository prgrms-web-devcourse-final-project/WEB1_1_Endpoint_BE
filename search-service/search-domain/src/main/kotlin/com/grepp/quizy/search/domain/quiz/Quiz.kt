package com.grepp.quizy.search.domain.quiz

class Quiz(
    val id: Long,
    val question: String,
    private val type: QuizType,
    private val tag: QuizTag,
    private val choice: QuizChoice,
    private val like: QuizLikeCount,
    private val comment: QuizCommentCount,
) {
    fun getTypeName(): String = type.typeName

    fun getTags(): String = tag.buildTagString()

    fun getChoices(): List<String> = choice.choices

    fun getAnswer(): String = choice.answer

    fun getExplanation(): String = choice.explanation

    fun getNumLikes(): Int = like.value

    fun getNumComments(): Int = comment.value
}
