package com.grepp.quizy.search.domain.quiz

interface GameSearchUseCase {
    fun searchForPrivateGame(category: String, size: Int, difficultyType: QuizDifficultyType): List<GameQuizDetail>
}