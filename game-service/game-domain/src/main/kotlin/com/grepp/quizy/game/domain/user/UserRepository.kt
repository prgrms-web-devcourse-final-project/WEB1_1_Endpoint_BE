package com.grepp.quizy.game.domain.user

interface UserRepository {

    fun save(user: User): User

    fun findById(userId: Long): User?

    fun findByIdIn(userIds: List<Long>): List<User>

    fun findRatingById(id: Long): Int?

}