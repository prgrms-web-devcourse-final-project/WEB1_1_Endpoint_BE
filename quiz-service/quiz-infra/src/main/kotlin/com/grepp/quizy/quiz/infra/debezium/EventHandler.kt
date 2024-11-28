package com.grepp.quizy.quiz.infra.debezium

interface EventHandler {
    fun process(event: DebeziumEvent)
}