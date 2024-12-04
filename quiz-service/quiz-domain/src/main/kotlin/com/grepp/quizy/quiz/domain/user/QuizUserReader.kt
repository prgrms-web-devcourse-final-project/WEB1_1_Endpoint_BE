package com.grepp.quizy.quiz.domain.user

import com.grepp.quizy.quiz.domain.user.exception.QuizUserException
import org.springframework.stereotype.Component

@Component
class QuizUserReader(
    private val quizUserRepository: QuizUserRepository,
    private val quizUserCache: QuizUserCache
) {

    fun read(id: UserId): QuizUser {
        return quizUserCache.getById(id) ?: readAndCache(id)
    }

    fun readIn(userIds: List<UserId>): List<QuizUser> =
        quizUserRepository.findByIdIn(userIds)

    private fun readAndCache(id: UserId): QuizUser {
        val quizUser = quizUserRepository.findById(id) ?: throw QuizUserException.NotFound
        return quizUserCache.cache(quizUser)
    }
}