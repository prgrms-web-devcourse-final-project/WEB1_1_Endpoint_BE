package com.grepp.quizy.user.infra.user.messaging.listener

import com.google.common.collect.Maps
import com.grepp.quizy.user.infra.user.messaging.listener.quiz.QuizEventHandler
import org.springframework.stereotype.Component

@Component
class EventHandlerFactory(
    private val quizEventHandler: QuizEventHandler,
    private val handlers: MutableMap<String, EventHandler> = Maps.newConcurrentMap()
) {

    companion object {
        private const val QUIZ = "quiz-service"
    }

    init {
        handlers[QUIZ] = quizEventHandler
    }

    fun getEventHandler(originService: String): EventHandler {
        return handlers[originService] ?: throw IllegalArgumentException("$originService 에 대한 핸들러가 없습니다.")
    }
}