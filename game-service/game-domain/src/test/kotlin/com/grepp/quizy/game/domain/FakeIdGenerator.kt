package com.grepp.quizy.game.domain

class FakeIdGenerator() : IdGenerator {

    private var currentId = 0L

    override fun generate(key: String): Long = ++currentId

    fun reset() {
        currentId = 0L
    }

}