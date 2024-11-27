package com.grepp.quizy.quiz.infra.debezium

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.GenericTypeResolver
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer

abstract class AbstractSimpleEventHandler<T>(
    private val mapper: ObjectMapper,
    protected val actions: MutableMap<DebeziumEvent.DebeziumEventPayloadOperation, BiConsumer<T?, T?>> = ConcurrentHashMap()
) {

    abstract fun initActions()

    fun process(event: DebeziumEvent) {
        val payload = event.payload
        val operation = payload.operation
        val payloadBefore = payload.before
        val payloadAfter = payload.after


        val entityClass = GenericTypeResolver.resolveTypeArgument(
            javaClass,
            AbstractSimpleEventHandler::class.java
        ) as Class<T>

        require(!Objects.isNull(entityClass)) { "AbstractSimpleEventHandler should have a type a argument" }

        val before = mapper.convertValue(payloadBefore, entityClass)
        val after = mapper.convertValue(payloadAfter, entityClass)

        actions[operation]?.accept(before, after) ?: throw IllegalStateException("Unsupported operation: $operation")
    }
}