package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.common.dto.DateTime
import com.grepp.quizy.quiz.domain.quiz.exception.QuizException
import com.grepp.quizy.quiz.domain.useranswer.UserId

sealed class Quiz(
        val userId: UserId,
        val type: QuizType,
        private var _content: QuizContent,
        val id: QuizId,
        val dateTime: DateTime,
        private var _commentCount: Long,
) {

    val content: QuizContent
        get() = this._content

    val commentCount: Long
        get() = this._commentCount

    protected fun validateOptions(requiredSize: Int) {
        require(_content.options.size == requiredSize) {
            "퀴즈 옵션의 갯수는 ${requiredSize}개 이어야 하지만 현재 ${_content.options.size}개 입니다"
        }
    }

    fun updateContent(userId: UserId, newContent: QuizContent): Quiz {
        validateOwner(userId)
        this._content = newContent
        return this
    }

    fun validateOwner(userId: UserId) {
        require(this.userId == userId) { QuizException.NoPermission }
    }

    fun increaseCommentCount() {
        this._commentCount++
    }
}
