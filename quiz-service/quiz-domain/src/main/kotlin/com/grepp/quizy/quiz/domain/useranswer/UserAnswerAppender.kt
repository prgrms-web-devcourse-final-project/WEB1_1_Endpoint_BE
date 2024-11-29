package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.quiz.Answerable
import com.grepp.quizy.quiz.domain.quiz.Quiz
import org.springframework.stereotype.Component

@Component
class UserAnswerAppender(
        private val userAnswerRepository: UserAnswerRepository
) {

    fun append(
        quiz: Quiz,
        id: UserAnswerId,
        userChoice: Int,
    ): UserAnswer {
        val userAnswer =
                when (quiz) {
                    is Answerable ->
                            UserAnswer.create(
                                    id,
                                    userChoice,
                                    quiz.isCorrect(userChoice),
                            )
                    else ->
                            UserAnswer.create(
                                    id,
                                    userChoice,
                            )
                }
        return userAnswerRepository.save(userAnswer)
    }
}
