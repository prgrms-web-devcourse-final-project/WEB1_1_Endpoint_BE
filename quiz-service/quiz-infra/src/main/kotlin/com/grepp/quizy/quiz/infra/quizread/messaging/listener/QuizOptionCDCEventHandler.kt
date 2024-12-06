package com.grepp.quizy.quiz.infra.quizread.messaging.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.quiz.infra.debezium.AbstractSimpleEventHandler
import com.grepp.quizy.quiz.infra.debezium.DebeziumEvent
import com.grepp.quizy.quiz.infra.debezium.EventHandler
import org.springframework.stereotype.Component

@Component
class QuizOptionCDCEventHandler(
    mapper: ObjectMapper,
    private val quizDocumentSynchronizer: QuizDocumentSynchronizer
) : AbstractSimpleEventHandler<QuizOptionCDCEvent>(mapper), EventHandler {
    init {
        initActions()
    }

    final override fun initActions() {
        actions.put(DebeziumEvent.DebeziumEventPayloadOperation.CREATE) { _, after ->
            after?.let { quizDocumentSynchronizer.addQuizOption(it) }
        }

        actions.put(DebeziumEvent.DebeziumEventPayloadOperation.DELETE) { before, _ ->
            before?.let { quizDocumentSynchronizer.removeQuizOption(it.quizId, it.optionNumber) }
        }
    }
}