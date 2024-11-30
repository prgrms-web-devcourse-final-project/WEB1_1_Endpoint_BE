package com.grepp.quizy.quiz.domain.user

interface QuizUserRepository {

    fun findById(id: UserId): QuizUser?

    fun findByIdIn(userIds: List<UserId>): List<QuizUser>

    fun save(quizUser: QuizUser)

    fun deleteById(id: UserId)

}