package com.grepp.quizy.game.domain

enum class MessageType {
    GAME_ROOM,
    ANSWER_SUBMITTED,
    QUIZ_TRANSMITTED,
    SCORE_BOARD,
    GAME_END
}

sealed interface MessagePayload

data class GameMessage(
    val gameId: Long,
    val type: MessageType,
    val timestamp: Long = System.currentTimeMillis(),
    val payload: MessagePayload
) {
    companion object {
        fun room(gameId: Long, payload: MessagePayload): GameMessage {
            return GameMessage(
                gameId = gameId,
                type = MessageType.GAME_ROOM,
                payload = payload
            )
        }
    }
}

data class GamePayload(
    val setting: GameSetting,
    val status: GameStatus,
    val players: Players,
    val inviteCode: InviteCode
) : MessagePayload {
    companion object {
        fun from(game: Game): GamePayload {
            return GamePayload(
                setting = game.setting,
                status = game.status,
                players = game.players,
                inviteCode = game.inviteCode
            )
        }
    }
}

// TODO: Implement other message payloads
