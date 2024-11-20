package com.grepp.quizy.user.domain.user

interface UserRepository {
    fun findById(id: Long): User?
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    fun save(user: User): User
    fun delete(user: User)
}