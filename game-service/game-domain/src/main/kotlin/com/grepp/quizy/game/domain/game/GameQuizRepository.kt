package com.grepp.quizy.game.domain.game

interface GameQuizRepository {

    fun saveQuiz(gameId: Long, quizId: Long): Long?

    fun findQuizzes(gameId: Long): Set<Long>

}