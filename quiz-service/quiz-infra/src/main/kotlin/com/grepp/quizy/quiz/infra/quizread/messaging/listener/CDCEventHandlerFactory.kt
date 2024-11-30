package com.grepp.quizy.quiz.infra.quizread.messaging.listener

import com.google.common.collect.Maps
import com.grepp.quizy.quiz.infra.debezium.EventHandler
import org.springframework.stereotype.Component
import java.util.*

@Component
class CDCEventHandlerFactory(
    private val quizCDCEventHandler: QuizCDCEventHandler,
    private val quizOptionCDCEventHandler: QuizOptionCDCEventHandler,
    private val quizTagMappingCDCEventHandler: QuizTagMappingCDCEventHandler,
    private val handlers: MutableMap<String, EventHandler> = Maps.newConcurrentMap()
) {

    companion object {
        private const val QUIZZES = "quizzes"
        private const val QUIZ_OPTIONS = "quiz_options"
        private const val QUIZ_TAGS = "quiz_tags_mapping"
    }


    init {
        handlers[QUIZZES] = quizCDCEventHandler
        handlers[QUIZ_OPTIONS] = quizOptionCDCEventHandler
        handlers[QUIZ_TAGS] = quizTagMappingCDCEventHandler
    }

    fun getHandler(tableName: String): EventHandler {

        return Optional.ofNullable(handlers[tableName])
            .orElseThrow {
                IllegalArgumentException(
                    "$tableName 토픽에 대한 핸들러가 없습니다."
                )
            }
    }

}