package com.grepp.quizy.common.event

import java.time.LocalDateTime

interface ServiceEvent {
    val eventType: String
    val aggregateType: String
    val aggregateId: Long
    val timestamp: LocalDateTime
    fun toPayload(): Map<String, Any>
    fun getOrigin(): String
}