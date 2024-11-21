package com.grepp.quizy.game.domain

interface GameRepository {

    fun save(game: Game): Game

    fun findById(id: Long): Game?

    fun findByInviteCode(code: String): Game?
}
