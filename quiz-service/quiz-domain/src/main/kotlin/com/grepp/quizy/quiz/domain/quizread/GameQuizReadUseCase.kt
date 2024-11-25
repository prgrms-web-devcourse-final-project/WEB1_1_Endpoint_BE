package com.grepp.quizy.quiz.domain.quizread

interface GameQuizReadUseCase {
    fun searchForPrivateGame(condition: GameQuizSearchCondition): List<GameQuizDetail>
}