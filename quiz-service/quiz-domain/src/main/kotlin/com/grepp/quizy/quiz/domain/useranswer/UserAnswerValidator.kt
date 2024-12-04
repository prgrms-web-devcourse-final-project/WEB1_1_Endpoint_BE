package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.useranswer.exception.UserAnswerException
import org.springframework.stereotype.Component

@Component
class UserAnswerValidator(
    private val userAnswerRepository: UserAnswerRepository
) {
    fun validate(userAnswerKey: UserAnswerKey) {
        if (userAnswerRepository.existsById(userAnswerKey)) {
            throw UserAnswerException.AlreadyExists
        }
    }
}