package com.grepp.quizy.game.domain.game

interface GameRepository {

    fun save(game: Game): Game

    fun findById(id: Long): Game?

    fun findByInviteCode(code: String): Game?

    fun saveQuiz(gameId: Long, quizId: Long)

}
