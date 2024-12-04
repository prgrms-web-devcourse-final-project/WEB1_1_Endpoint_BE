package com.grepp.quizy.quiz.domain.user

import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.useranswer.events.UserAnsweredEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuizUserService(
    private val quizUserReader: QuizUserReader,
    private val quizUserManager: QuizUserManager,
    private val quizUserCache: QuizUserCache
) {
    fun getUser(id: UserId): QuizUser {
        return quizUserReader.read(id)
    }

    fun getAchievements(id: UserId): List<QuizUserAchievement> {
        return quizUserReader.read(id).achievements
    }

    fun addInterests(id: UserId, interests: List<QuizCategory>) {
        val quizUser = quizUserReader.read(id)
        quizUserManager.initInterests(quizUser, interests)
    }

    fun updateInterests(id: UserId, interests: List<QuizCategory>) {
        val quizUser = quizUserReader.read(id)
        quizUserManager.updateInterests(quizUser, interests)
    }

    @Transactional
    @EventListener
    fun handle(event: UserAnsweredEvent) {
        val user = quizUserReader.read(event.userId)
        user.applyAnsweredEvent(event)
        quizUserCache.cache(user)
    }
}