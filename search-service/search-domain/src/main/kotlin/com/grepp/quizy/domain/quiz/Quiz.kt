package com.grepp.quizy.domain.quiz

class Quiz(
    val id: Long,
    val question: String,
    private val type: QuizType,
    private val tag: QuizTag,
    private val choice: QuizChoice,
    private val like: QuizLike,
    private val comment: QuizComment,
) {
    fun getTypeName(): String = type.typeName

    fun getTags(): String = tag.buildTagString()

    fun getChoices(): List<String> = choice.choices

    fun getAnswer(): String = choice.answer

    fun getExplanation(): String = choice.explanation

    fun getNumLikes(): Int = like.numLikes

    fun getNumComments(): Int = comment.numComments
}
