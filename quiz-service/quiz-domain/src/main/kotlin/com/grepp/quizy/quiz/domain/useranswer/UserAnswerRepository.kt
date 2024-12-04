package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.common.dto.Cursor
import com.grepp.quizy.common.dto.SliceResult
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId

interface UserAnswerRepository {

    fun save(userAnswer: UserAnswer): UserAnswer

    fun findById(key: UserAnswerKey): UserAnswer?

    fun findAllByUserAnswerId(userAnswerKeys: List<UserAnswerKey>): UserAnswerPackage

    fun findAllByUserId(userId: UserId): List<QuizId>

    fun findAllByUserIdAndIsCorrect(userId: UserId, isCorrect: Boolean, reviewStatus: ReviewStatus, cursor: Cursor): SliceResult<UserAnswer>

    fun existsById(key: UserAnswerKey): Boolean
}
