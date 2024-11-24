package com.grepp.quizy.search.domain.quiz

interface GameQuizSearchUseCase {
    fun searchForPrivateGame(condition: GameQuizSearchCondition): List<GameQuizDetail>
}