package com.grepp.quizy.quiz.domain.quiz

abstract class Quiz(
        val type: QuizType,
        private var _content: QuizContent,
        val id: QuizId,
        val dateTime: QuizDateTime,
) {
    val content: QuizContent
        get() = this._content

    protected fun validateOptions(requiredSize: Int) {
        require(_content.options.size == requiredSize) {
            "퀴즈 옵션의 갯수는 ${requiredSize}개 이어야 하지만 현재 ${_content.options.size}개 입니다"
        }
    }

    fun updateContent(newContent: QuizContent): Quiz {
        this._content = newContent
        return this
    }
}
