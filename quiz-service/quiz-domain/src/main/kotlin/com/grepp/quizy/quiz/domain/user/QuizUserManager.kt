package com.grepp.quizy.quiz.domain.user

import com.grepp.quizy.common.event.EventPublisher
import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.user.events.UserInterestsInitializedEvent
import org.springframework.stereotype.Component

@Component
class QuizUserManager(
    private val quizUserRepository: QuizUserRepository,
    private val quizUserReader: QuizUserReader,
    private val quizEventPublisher: EventPublisher,
    private val quizUserCache: QuizUserCache
) {

    fun createUser(id: UserId, profile: UserProfile) {
        val user = QuizUser(id = id, profile = profile)
        quizUserRepository.save(user)
        quizUserCache.cache(user)
    }

    fun initInterests(user: QuizUser, interests: List<QuizCategory>) {
        user.addInterests(interests)
        quizUserRepository.save(user)
        quizUserCache.cache(user)
        quizEventPublisher.publish(UserInterestsInitializedEvent.from(user))
    }

    fun updateInterests(user: QuizUser, interests: List<QuizCategory>) {
        user.addInterests(interests)
        quizUserCache.cache(user)
        quizUserRepository.save(user)
    }

    fun update(id: UserId, profile: UserProfile) {
        val user = quizUserReader.read(id)
        user.update(profile)
        quizUserRepository.save(user)
        quizUserCache.cache(user)
    }

    fun remove(id: UserId) {
        quizUserRepository.deleteById(id)
        quizUserCache.evict(id)
    }


}
