package com.grepp.quizy.quiz.domain.useranswer


sealed class Choice {
    abstract val choiceNumber: Int

    data class AnswerableChoice(
        override val choiceNumber: Int,
        val isCorrect: Boolean,
    ) : Choice()

    data class NonAnswerableChoice(
        override val choiceNumber: Int,
    ) : Choice()

    companion object {
        fun create(choiceNumber: Int, isCorrect: Boolean? = null): Choice =
            when (isCorrect) {
                null -> NonAnswerableChoice(choiceNumber)
                else -> AnswerableChoice(choiceNumber, isCorrect)
            }
    }
}
