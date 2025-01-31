package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.common.dto.DateTime
import com.grepp.quizy.quiz.domain.quiz.exception.QuizException
import com.grepp.quizy.quiz.domain.user.UserId

sealed class Quiz(
    val creatorId: UserId,
    val type: QuizType,
    private var _content: QuizContent,
    val id: QuizId,
    private var _dateTime: DateTime,
    private var _commentCount: Long,
    private var _likeCount: Long,
) {

    val content: QuizContent
        get() = this._content

    val commentCount: Long
        get() = this._commentCount

    val likeCount: Long
        get() = this._likeCount

    val dateTime: DateTime
        get() = this._dateTime


    protected fun validateOptions(requiredSize: Int) {
        require(_content.options.size == requiredSize) {
            "퀴즈 옵션의 갯수는 ${requiredSize}개 이어야 하지만 현재 ${_content.options.size}개 입니다"
        }
    }

    fun getTotalAnsweredCount(): Long {
        return content.options.sumOf { it.selectionCount }
    }

    fun updateContent(userId: UserId, newContent: QuizContent): Quiz {
        validateOwner(userId)
        this._content = _content.update(newContent)
        return this
    }

    fun validateOwner(userId: UserId) {
        require(this.creatorId == userId) { QuizException.NoPermission }
    }

    fun increaseCommentCount() {
        this._commentCount++
    }

    fun decreaseCommentCount() {
        this._commentCount--
    }
}
