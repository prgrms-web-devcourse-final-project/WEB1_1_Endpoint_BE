package com.grepp.quizy.quiz.domain.user

import com.grepp.quizy.quiz.domain.user.exception.QuizUserException
import org.springframework.stereotype.Component

@Component
class QuizUserReader(
    private val quizUserRepository: QuizUserRepository
) {

    fun read(id: UserId): QuizUser =
        quizUserRepository.findById(id) ?: throw QuizUserException.NotFound

    fun readIn(userIds: List<UserId>): List<QuizUser> =
        quizUserRepository.findByIdIn(userIds)
}