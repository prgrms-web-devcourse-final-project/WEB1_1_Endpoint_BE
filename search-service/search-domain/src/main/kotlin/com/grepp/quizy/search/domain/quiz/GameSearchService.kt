package com.grepp.quizy.search.domain.quiz

import org.springframework.stereotype.Component

@Component
class GameSearchService(
    private val quizSearcher: QuizSearcher
) : GameSearchUseCase {

    override fun searchForPrivateGame(condition: GameQuizSearchCondition): List<GameQuizDetail> {
        val poolSize = GameQuizPoolUtils.expandQuizPoolSize(condition.size)
        val candidates = quizSearcher.search(GameQuizSearchCondition(condition.category, condition.difficulty, poolSize))
        return GameQuizPoolUtils.makeGameSet(candidates, condition.size)
    }
}