package com.grepp.quizy.game.domain.quiz

import com.grepp.quizy.game.domain.game.IdGenerator
import org.springframework.stereotype.Component

@Component
class QuizAppender(
    private val quizRepository: QuizRepository,
    private val idGenerator: IdGenerator
) {

    fun appendAll(
        quizzes: List<GameQuiz>
    ): List<GameQuiz> {
        quizzes.map {
            // 실제 외부에서 준 ID는 내가 사용 할 이유가 없음(어차피 1회성 id임 다른 서비스와 정합성 필요x)
            GameQuiz(
                id = idGenerator.generate("quiz"),
                content = it.content,
                answer = it.answer,
                options = it.options
            )
        }
        return quizRepository.saveAll(quizzes)
    }

}