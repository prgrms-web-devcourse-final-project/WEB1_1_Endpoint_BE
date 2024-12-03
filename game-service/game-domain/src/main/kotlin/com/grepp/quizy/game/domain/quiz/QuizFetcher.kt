package com.grepp.quizy.game.domain.quiz

import com.grepp.quizy.game.domain.game.GameLevel
import com.grepp.quizy.game.domain.game.GameSubject

interface QuizFetcher {

    fun fetchQuiz(
        category: GameSubject,
        size: Int,
        difficulty: GameLevel
    ): Quizzes

}