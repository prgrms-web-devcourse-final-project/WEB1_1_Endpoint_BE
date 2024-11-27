package com.grepp.quizy.quiz.domain.user

interface UserRepository {

    fun save(user: User): User

    fun findById(userId: UserId): User?

    fun findByIdIn(userIds: List<UserId>): List<User>

}