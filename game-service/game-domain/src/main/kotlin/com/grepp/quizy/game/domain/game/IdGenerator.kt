package com.grepp.quizy.game.domain.game

interface IdGenerator {
    fun generate(key: String): Long
}
