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
    ERROR,
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

        fun quizAnswer(
            gameId: Long,
            payload: MessagePayload
        ): GameMessage {
            return GameMessage(
                gameId = gameId,
                type = MessageType.ANSWER_SUBMITTED,
                payload = payload,
            )
        }

        fun leaderboard(
            gameId: Long,
            payload: MessagePayload
        ): GameMessage {
            return GameMessage(
                gameId = gameId,
                type = MessageType.SCORE_BOARD,
                payload = payload,
            )
        }

        fun error(
            gameId: Long = 0, // 안쓰는 값이라서 0으로 초기화
            payload: MessagePayload
        ): GameMessage {
            return GameMessage(
                gameId = gameId,
                type = MessageType.ERROR,
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

data class QuizAnswerPayload(
    val score: Double,
    val correct: Boolean,
    val answer: String,
    val explanation: String,
) : MessagePayload {
    companion object {
        fun of(gameQuiz: GameQuiz, score: Double, correct: Boolean): QuizAnswerPayload {
            return QuizAnswerPayload(
                score = score,
                correct = correct,
                answer = gameQuiz.answer.content,
                explanation = gameQuiz.answer.explanation,
            )
        }
    }
}

data class LeaderboardPayload(
    val leaderboard: List<LeaderboardInfo>
) : MessagePayload {
    companion object {
        fun from(leaderboard: List<LeaderboardInfo>): LeaderboardPayload {
            return LeaderboardPayload(
                leaderboard = leaderboard
            )
        }
    }

}

data class LeaderboardInfo(
    val userId: Long,
    val score: Double
) {
    companion object {
        fun of(userId: Long, score: Double): LeaderboardInfo {
            return LeaderboardInfo(
                userId = userId,
                score = score
            )
        }
    }
}

data class ErrorPayload(
    val errorCode: String,
    val message: String,
) : MessagePayload {
    companion object {
        fun of(errorCode: String = "INTERNAL_SERVER_ERROR", message: String): ErrorPayload {
            return ErrorPayload(
                errorCode = errorCode,
                message = message
            )
        }
    }
}

// TODO: Implement other message payloads
