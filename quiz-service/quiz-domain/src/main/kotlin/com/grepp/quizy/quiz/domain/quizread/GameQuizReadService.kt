package com.grepp.quizy.quiz.domain.quizread

import org.springframework.stereotype.Component

@Component
class GameQuizReadService(
    private val quizSearcher: QuizSearcher
) : GameQuizReadUseCase {

    override fun searchForPrivateGame(condition: GameQuizSearchCondition): List<GameQuizDetail> {
        val poolSize = GameQuizPoolUtils.expandQuizPoolSize(condition.size)
        val candidates = quizSearcher.search(GameQuizSearchCondition(condition.category, condition.difficulty, poolSize))
        return GameQuizPoolUtils.makeGameSet(candidates, condition.size)
    }
}