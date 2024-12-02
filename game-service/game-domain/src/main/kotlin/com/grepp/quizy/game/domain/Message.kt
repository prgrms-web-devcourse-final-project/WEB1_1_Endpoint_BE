package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.game.Game
import com.grepp.quizy.game.domain.game.Player
import com.grepp.quizy.game.domain.quiz.GameQuiz
import com.grepp.quizy.game.domain.quiz.GameQuizOption

enum class MessageType {
    GAME_ROOM,
    CHAT,
    ANSWER_SUBMITTED,
    QUIZ_TRANSMITTED,
    SCORE_BOARD,
    GAME_END,
    KICKED,
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
        fun room(game: Game): GameMessage {
            return GameMessage(
                gameId = game.id,
                type = MessageType.GAME_ROOM,
                payload = RoomPayload.from(game),
            )
        }

        fun chat(gameId: Long, userId: Long, message: String): GameMessage {
            return GameMessage(
                gameId = gameId,
                type = MessageType.CHAT,
                payload = ChatPayload.from(userId, message),
            )
        }

        fun quiz(
            gameId: Long,
            quizzes: List<GameQuiz>
        ): GameMessage {
            return GameMessage(
                gameId = gameId,
                type = MessageType.QUIZ_TRANSMITTED,
                payload = QuizPayload.from(quizzes),
            )
        }

        fun quizAnswer(
            gameId: Long,
            gameQuiz: GameQuiz,
            score: Int,
            correct: Boolean
        ): GameMessage {
            return GameMessage(
                gameId = gameId,
                type = MessageType.ANSWER_SUBMITTED,
                payload = QuizAnswerPayload.of(gameQuiz, score, correct),
            )
        }

        fun leaderboard(
            gameId: Long,
            leaderboard: List<LeaderboardInfo>
        ): GameMessage {
            return GameMessage(
                gameId = gameId,
                type = MessageType.SCORE_BOARD,
                payload = LeaderboardPayload.from(leaderboard),
            )
        }

        fun kick(
            gameId: Long,
            targetUserId: Long
        ): GameMessage {
            return GameMessage(
                gameId = gameId,
                type = MessageType.KICKED,
                payload = KickedPayload.from(targetUserId),
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
    val subject: String,
    val level: String,
    val quizCount: Int,
    val status: String,
    val inviteCode: String?,
    val players: List<UserInfoMessage>
) : MessagePayload {
    companion object {
        fun from(game: Game): RoomPayload {
            return RoomPayload(
                subject = game.setting.subject.description,
                level = game.setting.level.description,
                quizCount = game.setting.quizCount,
                status = game.status.description,
                inviteCode = game.inviteCode?.value,
                players = game.players.players.map { UserInfoMessage.from(it) }
            )
        }
    }
}

data class UserInfoMessage(
    val id: Long,
    val name: String,
    val imgPath: String,
    val rating: Int,
    val role: String,
    val host: Boolean,
    val score: Int = 0
) {
    companion object {
        fun from(player: Player): UserInfoMessage {
            return UserInfoMessage(
                id = player.user.id,
                name = player.user.name,
                imgPath = player.user.imgPath,
                rating = player.user.rating,
                host = player.isHost(),
                role = player.role.name
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
    val score: Int,
    val correct: Boolean,
    val answer: String,
    val explanation: String,
) : MessagePayload {
    companion object {
        fun of(gameQuiz: GameQuiz, score: Int, correct: Boolean): QuizAnswerPayload {
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
    val score: Int
) {
    companion object {
        fun of(userId: Long, score: Int): LeaderboardInfo {
            return LeaderboardInfo(
                userId = userId,
                score = score
            )
        }
    }
}

data class KickedPayload(
    val userId: Long,
) : MessagePayload {
    companion object {
        fun from(userId: Long): KickedPayload {
            return KickedPayload(
                userId = userId,
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
