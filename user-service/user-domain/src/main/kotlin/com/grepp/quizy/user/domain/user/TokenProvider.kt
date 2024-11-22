package com.grepp.quizy.user.domain.user

interface TokenProvider {
    fun getExpiration(token: String): Long
}