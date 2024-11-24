package com.grepp.quizy.search.domain.quiz

interface GameSearchUseCase {
    fun searchForPrivateGame(condition: GameQuizSearchCondition): List<GameQuizDetail>
}