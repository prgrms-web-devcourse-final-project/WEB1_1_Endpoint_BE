package com.grepp.quizy.quiz.api.quiz

import com.grepp.quizy.quiz.domain.*

data class CreateQuizRequest(
        val type: QuizType,
        val content: String,
        val answer: String?,
        val explanation: String?,
        val tags: List<String>,
        val options: List<QuizOption>,
) {
    fun toContent(): QuizContent =
            QuizContent(type, content)

    fun toAnswer(): QuizAnswer =
            when (type) {
                QuizType.AB_TEST -> QuizAnswer.EMPTY
                else ->
                        answer?.let {
                            QuizAnswer(
                                    it,
                                    explanation
                                            ?: throw IllegalArgumentException(
                                                    "정답이 있는 문제는 해설이 필수입니다"
                                            ),
                            )
                        }
                                ?: throw IllegalArgumentException(
                                        "AB 테스트가 아닌 경우 정답은 필수입니다"
                                )
            }

    fun toQuizTags(): List<QuizTag> =
            tags.map { QuizTag.from(it) }
}
