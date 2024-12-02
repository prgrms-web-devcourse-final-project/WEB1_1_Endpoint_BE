package com.grepp.quizy.common.event

interface EventPublisher {

    fun publish(event: ServiceEvent)
}