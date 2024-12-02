package com.grepp.quizy.quiz.domain.user

import com.grepp.quizy.common.event.EventPublisher
import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import org.springframework.stereotype.Component

@Component
class QuizUserManager(
    private val quizUserRepository: QuizUserRepository,
    private val quizEventPublisher: EventPublisher

) {
    fun initInterests(user: QuizUser, interests: List<QuizCategory>) {
        user.addInterests(interests)
        quizUserRepository.save(user)
        quizEventPublisher.publish(UserInterestsInitializedEvent.from(user))
    }

    fun updateInterests(user: QuizUser, interests: List<QuizCategory>) {
        user.addInterests(interests)
        quizUserRepository.save(user)
    }
}
