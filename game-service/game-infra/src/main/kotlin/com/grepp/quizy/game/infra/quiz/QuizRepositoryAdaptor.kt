package com.grepp.quizy.game.infra.quiz

import com.grepp.quizy.game.domain.quiz.GameQuiz
import com.grepp.quizy.game.domain.quiz.QuizRepository
import org.springframework.stereotype.Repository

@Repository
class QuizRepositoryAdaptor(
    private val quizRedisRepository: QuizRedisRepository,
) : QuizRepository {

    override fun saveAll(quizzes: List<GameQuiz>): List<GameQuiz> =
        quizRedisRepository.saveAll(quizzes.map(QuizRedisEntity::from)).map(QuizRedisEntity::toDomain)

}