package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.exception.GameException
import com.grepp.quizy.game.domain.user.User

enum class GameType(
    val description: String
) {
    PRIVATE("사설 게임"),
    RANDOM("랜덤 매칭 게임"),
    ;
}

enum class GameStatus(
    val description: String
) {
    WAITING("대기 중"),
    PLAYING("진행 중"),
    FINISHED("종료"),
    DELETED("삭제됨")

    ;

}

data class GameSetting(
    val subject: GameSubject,
    val level: GameLevel = GameLevel.RANDOM,
    val quizCount: Int = 10,
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

enum class GameSubject(
    val description: String
) {

    JAVASCRIPT("자바스크립트"),
    SPRING("스프링"),
    // TODO: 정해진 형식의 주제 10개를 Enum으로 정의한다.
    ;

}

enum class GameLevel(
    val description: String
) {

    EASY("쉬움"),
    NORMAL("보통"),
    HARD("어려움"),
    RANDOM("랜덤")

    ;
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

enum class PlayerStatus {
    WAITING,
    JOINED,
}

data class Player(
    val user: User,
    private var _role: PlayerRole = PlayerRole.GUEST,
    private var _status: PlayerStatus = PlayerStatus.JOINED
) {
    val role: PlayerRole
        get() = _role

    val status: PlayerStatus
        get() = _status

    fun join() {
        _status = PlayerStatus.JOINED
    }

    fun grantHost() {
        _role = PlayerRole.HOST
    }

    fun isGuest(): Boolean {
        return _role == PlayerRole.GUEST
    }

    fun isHost(): Boolean {
        return role == PlayerRole.HOST
    }

    fun isWaiting(): Boolean {
        return status == PlayerStatus.WAITING
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        return user == other.user
    }

    override fun hashCode(): Int {
        return user.hashCode()
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
        if (players.size >= 5) {
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
        if (player.isHost()) {
            val newPlayers = players - player
            newPlayers.firstOrNull()?.grantHost()
            return Players(newPlayers)
        }
        return Players(players - player)
    }

    fun findPlayer(user: User): Player {
        return players.find { it.user == user }
            ?: throw GameException.GameNotParticipatedException
    }

    fun joinRandomGame(user: User): Players =
        findPlayer(user)
            .takeIf {
                it.isWaiting()
            }
            ?.let {
                Players(updatePlayerStatus(user))
            }
            ?: throw GameException.GameAlreadyParticipatedException

    private fun updatePlayerStatus(user: User): List<Player> =
        players.map { player ->
            when (player.user) {
                user -> player.apply { join() }
                else -> player
            }
        }


    fun isEmpty(): Boolean {
        return players.isEmpty()
    }

    fun isAllParticipated(): Boolean {
        return players.map {
            it.isWaiting()
        }.all { !it }
    }


}