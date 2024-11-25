package com.grepp.quizy.game.domain

interface UserRepository {

    fun save(user: User): User

}