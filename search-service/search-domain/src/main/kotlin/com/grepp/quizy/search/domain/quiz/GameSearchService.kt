package com.grepp.quizy.search.domain.quiz

import org.springframework.stereotype.Component

@Component
class GameSearchService(
    private val quizSearcher: QuizSearcher
) : GameSearchUseCase {

    override fun searchForPrivateGame(
        category: String,
        size: Int,
        difficultyType: QuizDifficultyType
    ): List<GameQuizDetail> {
        val poolSize = GameQuizPoolUtils.expandQuizPoolSize(size)
        val candidates = quizSearcher.search(GameSearchCondition(category, difficultyType, poolSize))
        return GameQuizPoolUtils.makeGameSet(candidates, size)
    }
}