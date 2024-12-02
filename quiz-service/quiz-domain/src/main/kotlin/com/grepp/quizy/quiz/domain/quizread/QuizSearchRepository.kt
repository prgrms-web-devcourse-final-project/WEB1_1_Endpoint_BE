package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.quiz.Quiz
import com.grepp.quizy.quiz.domain.quiz.QuizId

interface QuizSearchRepository {

    fun save(quiz: Quiz)

    fun saveAll(quizList: List<Quiz>)

    fun findById(quizId: QuizId): Quiz?

    fun search(condition: UserSearchCondition): Slice<Quiz>

    fun searchNotIn(answeredQuizIds: List<QuizId>, condition: UserSearchCondition): Slice<Quiz>

    fun searchNotIn(answeredQuizIds: List<QuizId>, condition: FeedSearchCondition): Slice<Quiz>

    fun searchNotIn(condition: FeedSearchCondition): Slice<Quiz>

    fun search(condition: GameQuizSearchCondition): List<Quiz>

    fun searchTrendingKeyword(): List<String>
}
