package com.grepp.quizy.quiz.infra.debezium

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import java.io.Serializable
import java.util.*


data class DebeziumEvent(
    @JsonProperty("schema")
    val schema: Map<String, Any>,
    @JsonProperty("payload")
    val payload: DebeziumEventPayload
) : Serializable {



    enum class DebeziumEventPayloadOperation(
        @JsonValue
        val value: String
    ) {
        CREATE("c"), UPDATE("u"), DELETE("d"), READ("r");

    }

    data class DebeziumEventPayload(
        @JsonProperty("before")
        val before: Map<String, Any>?,
        @JsonProperty("after")
        val after: Map<String, Any>?,
        @JsonProperty("source")
        val source: Map<String, Any>,
        @JsonProperty("op")
        val operation: DebeziumEventPayloadOperation,
        @JsonProperty("ts_ns")
        val date: Long,
    )
}