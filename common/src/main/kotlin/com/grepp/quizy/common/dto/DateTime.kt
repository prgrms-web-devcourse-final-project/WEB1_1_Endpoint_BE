package com.grepp.quizy.common.dto

import java.time.LocalDateTime

data class DateTime(
        val createdAt: LocalDateTime?,
        val updatedAt: LocalDateTime?,
) {
    companion object {
        fun init(): DateTime {
            return DateTime(null, null)
        }
    }
}
