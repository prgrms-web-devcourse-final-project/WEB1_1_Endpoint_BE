package com.grepp.quizy.game.domain.user

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

class FakeUserRepository : UserRepository {

    private val users = ConcurrentHashMap<Long, User>()
    private var sequence = AtomicLong(0)


    override fun save(user: User): User {
        val savedUser = if (user.id == 0L) {
            User(
                id = sequence.incrementAndGet(),
                name = user.name,
                imgPath = user.imgPath,
            )
        } else {
            user
        }
        users[savedUser.id] = savedUser
        return savedUser
    }

    override fun findById(userId: Long): User? {
        return users[userId]
    }

    override fun findByIdIn(userIds: List<Long>): List<User> {
        return users.filter { it.key in userIds }.values.toList()
    }

    fun saveAll(users: List<User>): List<User> {
        return users.map { save(it) }
    }

    fun clear() {
        users.clear()
    }
}