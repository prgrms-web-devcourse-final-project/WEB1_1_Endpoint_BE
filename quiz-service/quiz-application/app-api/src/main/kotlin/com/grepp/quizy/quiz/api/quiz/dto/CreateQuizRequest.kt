package com.grepp.quizy.quiz.api.quiz.dto

import com.grepp.quizy.quiz.domain.quiz.*

data class CreateQuizRequest(
        val category: QuizCategory,
        val type: QuizType,
        val content: String,
        val answer: String?,
        val explanation: String?,
        val tags: List<String>,
        val options: List<QuizOptionRequest>,
) {

    fun toContent(): QuizContent =
            QuizContent(
                    category = category,
                    content = content,
                    tags = tags.map { QuizTag.create(it) },
                    options = options.map { it.toOption(type) },
            )

    fun toAnswer(): QuizAnswer =
            when (type) {
                QuizType.AB_TEST -> QuizAnswer.NONE
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
}
