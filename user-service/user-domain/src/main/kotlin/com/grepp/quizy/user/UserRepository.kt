package com.grepp.quizy.user

interface UserRepository {
    fun findById(id: Long): User
    fun existsByEmail(email: String): Boolean
    fun save(user: User): User
    fun delete(user: User)
}