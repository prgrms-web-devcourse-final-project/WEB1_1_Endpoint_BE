package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.game.*
import com.grepp.quizy.game.domain.quiz.GameQuiz
import com.grepp.quizy.game.domain.quiz.GameQuizOption

enum class MessageType {
    GAME_ROOM,
    CHAT,
    ANSWER_SUBMITTED,
    QUIZ_TRANSMITTED,
    SCORE_BOARD,
    GAME_END,
}

sealed interface MessagePayload

data class GameMessage(
    val gameId: Long,
    val type: MessageType,
    val timestamp: Long = System.currentTimeMillis(),
    val payload: MessagePayload,
) {
    companion object {
        fun room(gameId: Long, payload: MessagePayload): GameMessage {
            return GameMessage(
                gameId = gameId,
                type = MessageType.GAME_ROOM,
                payload = payload,
            )
        }

        fun chat(gameId: Long, payload: MessagePayload): GameMessage {
            return GameMessage(
                gameId = gameId,
                type = MessageType.CHAT,
                payload = payload,
            )
        }

        fun quiz(
            gameId: Long,
            payload: MessagePayload
        ): GameMessage {
            return GameMessage(
                gameId = gameId,
                type = MessageType.QUIZ_TRANSMITTED,
                payload = payload,
            )
        }

    }
}

data class RoomPayload(
    val setting: GameSetting,
    val status: GameStatus,
    val players: Players,
    val inviteCode: InviteCode?,
) : MessagePayload {
    companion object {
        fun from(game: Game): RoomPayload {
            return RoomPayload(
                game.setting,
                game.status,
                game.players,
                game.inviteCode,
            )
        }
    }
}

data class ChatPayload(
    val userId: Long,
    val message: String,
) : MessagePayload {
    companion object {
        fun from(userId: Long, message: String): ChatPayload {
            return ChatPayload(
                userId = userId,
                message = message,
            )
        }
    }
}

data class QuizPayload(
    val quiz: List<QuizInfo>,
) : MessagePayload {
    companion object {
        fun from(quiz: List<GameQuiz>): QuizPayload {
            return QuizPayload(
                quiz = quiz.map { QuizInfo.from(it) },
            )
        }
    }
}

data class QuizInfo(
    val id: Long,
    val content: String,
    val option: List<GameQuizOption>,
) {
    companion object {
        fun from(quiz: GameQuiz): QuizInfo {
            return QuizInfo(
                id = quiz.id,
                content = quiz.content,
                option = quiz.option,
            )
        }
    }
}


// TODO: Implement other message payloads
