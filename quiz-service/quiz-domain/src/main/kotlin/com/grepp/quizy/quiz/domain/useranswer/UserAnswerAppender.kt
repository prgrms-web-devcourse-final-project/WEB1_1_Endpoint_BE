package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.quiz.Answerable
import com.grepp.quizy.quiz.domain.quiz.Quiz
import com.grepp.quizy.quiz.domain.useranswer.events.UserAnsweredEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class UserAnswerAppender(
    private val userAnswerRepository: UserAnswerRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun append(
        quiz: Quiz,
        key: UserAnswerKey,
        userChoice: Int,
    ): UserAnswer {
        val userAnswer = UserAnswer.create(key, userChoice, quiz)
        val savedAnswer = userAnswerRepository.save(userAnswer)
        if (quiz is Answerable) {
            applicationEventPublisher.publishEvent(UserAnsweredEvent.from(quiz, savedAnswer))
        }
        return savedAnswer
    }
}
