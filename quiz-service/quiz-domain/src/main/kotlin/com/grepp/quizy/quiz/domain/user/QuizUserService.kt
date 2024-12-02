package com.grepp.quizy.quiz.domain.user

import com.grepp.quizy.common.event.EventPublisher
import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import org.springframework.stereotype.Service

@Service
class QuizUserService(
    private val quizUserReader: QuizUserReader,
    private val quizUserManager: QuizUserManager
) {

    fun addInterests(id: UserId, interests: List<QuizCategory>) {
        val quizUser = quizUserReader.read(id)
        quizUserManager.initInterests(quizUser, interests)
    }

    fun updateInterests(id: UserId, interests: List<QuizCategory>) {
        val quizUser = quizUserReader.read(id)
        quizUserManager.updateInterests(quizUser, interests)
    }
}