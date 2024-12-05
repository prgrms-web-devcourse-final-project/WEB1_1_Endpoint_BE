package com.grepp.quizy.game.domain.message

import java.time.LocalDateTime

interface StreamMessage {
    val streamKey: String
    val value: Map<String, String>
    val timestamp: LocalDateTime

    companion object {
        fun rating(message: Map<String, String>): StreamMessage {
            return RatingMessage(
                value = message
            )
        }
        fun gameDestroy(message: Map<String, String>): StreamMessage {
            return GameDestroyMessage(
                value = message
            )
        }
    }
}

private data class RatingMessage(
    override val streamKey: String = "rating-update",
    override val value: Map<String, String>,
    override val timestamp: LocalDateTime = LocalDateTime.now()
) : StreamMessage

private data class GameDestroyMessage(
    override val streamKey: String = "game-destroy",
    override val value: Map<String, String>,
    override val timestamp: LocalDateTime = LocalDateTime.now()
) : StreamMessage