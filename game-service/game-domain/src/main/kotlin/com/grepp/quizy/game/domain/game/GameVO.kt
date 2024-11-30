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

    ALGORITHM("알고리즘"),
    PROGRAMMING_LANGUAGE("프로그래밍 언어"),
    NETWORK("네트워크"),
    OPERATING_SYSTEM("운영체제"),
    WEB_DEVELOPMENT("웹 개발"),
    MOBILE_DEVELOPMENT("모바일 개발"),
    DEV_OPS("데브옵스/인프라"),
    DATABASE("데이터베이스"),
    SOFTWARE_ENGINEERING("소프트웨어 공학"),
    ;

    companion object {
        fun fromString(value: String): GameSubject {
            return entries.find { it.description == value }
                ?: throw GameException.GameSubjectNotFoundException
        }
    }
}

enum class GameLevel(
    val description: String
) {

    EASY("하"),
    NORMAL("중"),
    HARD("상"),
    RANDOM("랜덤")

    ;

    companion object {
        fun fromString(value: String): GameLevel {
            return entries.find { it.description == value }
                ?: throw GameException.GameLevelNotFoundException
        }
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
        if (players.any { it.user.id == player.user.id }) {
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

    fun findPlayer(userId: Long): Player {
        return players.find { it.user.id == userId }
            ?: throw GameException.GameNotParticipatedException
    }

    fun joinRandomGame(userId: Long): Players =
        findPlayer(userId)
            .takeIf {
                it.isWaiting()
            }
            ?.let {
                Players(updatePlayerStatus(it))
            }
            ?: throw GameException.GameAlreadyParticipatedException


    private fun updatePlayerStatus(player: Player): List<Player> =
        players.map { p ->
            when (p.user) {
                player.user -> player.join().let { player }
                else -> p
            }
        }

    fun isEmpty(): Boolean {
        return players.isEmpty()
    }

    fun isAllParticipated(): Boolean {
        // 임시로 2명 이상이면 게임 시작 가능하도록 수정
        return players.size >= 2 && players.none { it.isWaiting() }
//        return players.none() { it.isWaiting() }
    }


}
