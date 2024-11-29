package com.grepp.quizy.matching.infra.match.converter

import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.JsonNode

class FloatArrayDeserializer : JsonDeserializer<FloatArray>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): FloatArray {
        val node: JsonNode = p.codec.readTree(p)
        return node.map { it.asDouble().toFloat() }.toFloatArray()
    }
}
