package com.grepp.quizy.quiz.infra.quizread.messaging.listener.cdc

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.quiz.infra.debezium.AbstractSimpleEventHandler
import com.grepp.quizy.quiz.infra.debezium.DebeziumEvent
import com.grepp.quizy.quiz.infra.debezium.EventHandler
import org.springframework.stereotype.Component

@Component
class QuizCDCEventHandler(
    mapper: ObjectMapper,
    private val quizSynchronizer: QuizSynchronizer,
) : AbstractSimpleEventHandler<QuizCDCEvent>(mapper), EventHandler {

    init {
        initActions()
    }


    final override fun initActions() {
        actions.put(DebeziumEvent.DebeziumEventPayloadOperation.CREATE) { _, after ->
            after?.let { quizSynchronizer.createQuiz(it) }
        }

        actions.put(DebeziumEvent.DebeziumEventPayloadOperation.UPDATE) { _, after ->
            after?.let { quizSynchronizer.updateQuiz(it) }
        }
        actions.put(DebeziumEvent.DebeziumEventPayloadOperation.DELETE) { before, _ ->
            before?.let { quizSynchronizer.removeQuiz(it.id) }
        }
    }
}