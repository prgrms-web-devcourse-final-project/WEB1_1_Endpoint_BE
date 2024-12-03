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
        key: UserAnswerKey,
        userChoice: Int,
    ): UserAnswer {
        val userAnswer =
                when (quiz) {
                    is Answerable ->
                            UserAnswer.create(
                                    key,
                                    userChoice,
                                    quiz.isCorrect(userChoice),
                            )
                    else ->
                            UserAnswer.create(
                                    key,
                                    userChoice,
                            )
                }
        return userAnswerRepository.save(userAnswer)
    }
}
