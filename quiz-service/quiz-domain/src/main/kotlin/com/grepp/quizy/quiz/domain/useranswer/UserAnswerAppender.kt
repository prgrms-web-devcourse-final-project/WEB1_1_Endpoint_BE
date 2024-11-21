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
            choice: String,
    ): UserAnswer {
        val userAnswer =
                when (quiz) {
                    is Answerable ->
                            UserAnswer.createAnswerable(
                                    quiz.type,
                                    id,
                                    choice,
                                    quiz.isCorrect(choice),
                            )
                    else ->
                            UserAnswer.createNonAnswerable(
                                    quiz.type,
                                    id,
                                    choice,
                            )
                }
        return userAnswerRepository.save(userAnswer)
    }
}
