package com.grepp.quizy.quiz.domain

abstract class Quiz(
        val content: QuizContent,
        val tags: List<QuizTag>,
        val options: List<QuizOption>,
        val id: QuizId,
) {

    protected fun validateOptions(requiredSize: Int) {
        require(options.size == requiredSize) {
            "퀴즈 옵션의 갯수는 ${requiredSize}개 이어야 하지만 현재 ${options.size}개 입니다"
        }
    }
}
