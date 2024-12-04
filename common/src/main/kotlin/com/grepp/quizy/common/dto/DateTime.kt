package com.grepp.quizy.common.dto

import java.time.LocalDateTime

data class DateTime(
        val createdAt: LocalDateTime = LocalDateTime.now(),
        val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun init(): DateTime {
            return DateTime()
        }
    }
}
