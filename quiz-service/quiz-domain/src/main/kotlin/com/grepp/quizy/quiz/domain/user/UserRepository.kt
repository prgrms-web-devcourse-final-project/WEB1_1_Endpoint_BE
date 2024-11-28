package com.grepp.quizy.quiz.domain.user

interface UserRepository {

    fun findByIdIn(userIds: List<UserId>): List<User>
}