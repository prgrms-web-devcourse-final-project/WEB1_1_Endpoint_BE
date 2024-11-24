package com.grepp.quizy.game.domain

interface QuizFetcher {

    fun fetchQuiz(
        subject: GameSubject,
        quizCount: Int,
        level: GameLevel
    ): GameQuiz

}