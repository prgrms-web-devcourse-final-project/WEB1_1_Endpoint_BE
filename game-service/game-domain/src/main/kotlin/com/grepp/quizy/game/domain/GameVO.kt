package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.exception.GameException

data class GameSetting(
    private val subject: GameSubject,
    private val level: GameLevel,
    private val quizCount: Int,
) {
    fun updateSubject(subject: GameSubject): GameSetting {
        return copy(subject = subject)
    }

    fun updateLevel(level: GameLevel): GameSetting {
        return copy(level = level)
    }

    fun updateQuizCount(quizCount: Int): GameSetting {
        return copy(quizCount = quizCount)
    }
}

data class InviteCode(
    val value: String = generate()
) {
    companion object {
        private const val INVITE_CODE_LENGTH = 6
        private val ALLOWED_CHARS = ('A'..'Z') + ('0'..'9')

        fun generate(): String {
            return buildString {
                repeat(INVITE_CODE_LENGTH) {
                    append(ALLOWED_CHARS.random())
                }
            }
        }
    }
}

data class Player(
    val id: Long,
    val role: PlayerRole = PlayerRole.GUEST
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun isGuest(): Boolean {
        return role == PlayerRole.GUEST
    }

}

enum class PlayerRole {
    HOST,
    GUEST
}

data class Players(
    val players: List<Player>
) {

    fun add(player: Player): Players {
        if (players.size > 5) {
            throw GameException.GameAlreadyFullException
        }
        if (players.contains(player)) {
            throw GameException.GameAlreadyParticipatedException
        }
        return Players(players + player)
    }

    fun remove(player: Player): Players {

        if (!players.contains(player)) {
            throw GameException.GameNotParticipatedException
        }
        return Players(players - player)
    }

    fun findPlayerById(userId: Long): Player {
        return players.find { it.id == userId }
            ?: throw GameException.GameNotParticipatedException
    }


}
