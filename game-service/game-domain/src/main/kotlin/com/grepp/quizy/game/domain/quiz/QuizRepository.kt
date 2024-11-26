package com.grepp.quizy.game.domain.quiz

interface QuizRepository {

    fun saveAll(quizzes: List<GameQuiz>): List<GameQuiz>

    fun findById(id: Long): GameQuiz?

}