package com.grepp.quizy.quiz.infra.quizread.messaging.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.quiz.infra.debezium.AbstractSimpleEventHandler
import com.grepp.quizy.quiz.infra.debezium.DebeziumEvent
import com.grepp.quizy.quiz.infra.debezium.EventHandler
import org.springframework.stereotype.Component

@Component
class QuizOptionCDCEventHandler(
    mapper: ObjectMapper,
    private val quizSynchronizer: QuizSynchronizer
) : AbstractSimpleEventHandler<QuizOptionCDCEvent>(mapper), EventHandler {
    init {
        initActions()
    }

    final override fun initActions() {
        actions.put(DebeziumEvent.DebeziumEventPayloadOperation.CREATE) { _, after ->
            after?.let { quizSynchronizer.addQuizOption(it) }
        }

        actions.put(DebeziumEvent.DebeziumEventPayloadOperation.DELETE) { before, _ ->
            before?.let { quizSynchronizer.removeQuizOption(it.quizId, it.optionNumber) }
        }
    }
}