package com.grepp.quizy.game.domain

interface IdGenerator {
    fun generate(key: String): Long
}
