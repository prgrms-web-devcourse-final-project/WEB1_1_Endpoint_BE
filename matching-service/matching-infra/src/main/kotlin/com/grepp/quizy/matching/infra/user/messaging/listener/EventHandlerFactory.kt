package com.grepp.quizy.matching.infra.user.messaging.listener

import com.grepp.quizy.matching.infra.user.messaging.listener.quiz.QuizEventHandler
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class EventHandlerFactory(
    private val quizEventHandler: QuizEventHandler,
    private val handlers: MutableMap<String, EventHandler> = ConcurrentHashMap()
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