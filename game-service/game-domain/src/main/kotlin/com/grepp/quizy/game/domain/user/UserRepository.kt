package com.grepp.quizy.game.domain.user

interface UserRepository {

    fun save(user: User): User

}