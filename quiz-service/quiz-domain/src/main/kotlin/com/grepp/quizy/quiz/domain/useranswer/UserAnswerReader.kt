package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.common.dto.Cursor
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.domain.useranswer.exception.UserAnswerException
import org.springframework.stereotype.Component

@Component
class UserAnswerReader(private val repository: UserAnswerRepository) {

    fun read(key: UserAnswerKey) = repository.findById(key) ?: throw UserAnswerException.NotFound

    fun read(userId: UserId, quizIds: List<QuizId>) =
        repository.findAllByUserAnswerId(quizIds.map { UserAnswerKey(userId, it) })

    fun readAnswered(userId: UserId) = repository.findAllByUserId(userId)

    fun readIncorrect(userId: UserId, cursor: Cursor) = repository.findAllByUserIdAndIsCorrect(userId, false, ReviewStatus.NOT_REVIEWED, cursor)
}