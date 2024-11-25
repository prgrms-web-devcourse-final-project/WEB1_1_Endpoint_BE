package com.grepp.quizy.user.domain.user

interface TokenGenerator {
    fun generateRefreshToken(user: User): String
    fun generateAccessToken(user: User): String
}