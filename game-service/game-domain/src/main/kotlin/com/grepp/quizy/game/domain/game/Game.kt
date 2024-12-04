package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.exception.GameException
import com.grepp.quizy.game.domain.exception.GameException.GameAlreadyStartedException
import com.grepp.quizy.game.domain.exception.GameException.GameHostPermissionException
import com.grepp.quizy.game.domain.game.GameStatus.DELETED
import com.grepp.quizy.game.domain.game.GameStatus.WAITING
import com.grepp.quizy.game.domain.user.User

class Game(
    val id: Long = 0,
    val type: GameType,
    private var _setting: GameSetting,
    private var _status: GameStatus = WAITING,
    private var _players: Players,
    val inviteCode: InviteCode?
) {
    val setting: GameSetting
        get() = _setting

    val players: Players
        get() = _players

    val status: GameStatus
        get() = _status

    constructor(
        id: Long,
        subject: GameSubject,
        users: List<User>
    ) : this(
        id = id,
        type = GameType.RANDOM,
        _setting = GameSetting(subject),
        _players = Players(users.map { Player(user = it, _status = PlayerStatus.WAITING) }.toList()),
        inviteCode = null
    ) {
//        validatePlayerCount(users)
    }

    companion object {
        fun create(
            id: Long,
            subject: GameSubject,
            quizCount: Int,
            level: GameLevel,
            user: User
        ): Game {
            val game = Game(
                id = id,
                type = GameType.PRIVATE,
                _setting = GameSetting(subject = subject, level = level, quizCount = quizCount),
                _players = Players(listOf(Player(user, _role = PlayerRole.HOST))),
                inviteCode = InviteCode()
            )
            return game
        }

        fun random(
            id: Long,
            subject: GameSubject,
            users: List<User>
        ): Game {
            return Game(
                id = id,
                subject = subject,
                users = users
            )
        }
    }

    fun join(user: User) {
        validateGameNotStarted()
        this._players = _players.add(Player(user))
    }

    fun joinRandomGame(userId: Long) {
        this._players = _players.joinRandomGame(userId)
    }

    fun quit(userId: Long) {
        val player = _players.findPlayer(userId)
        this._players = _players.remove(player)
        if (_players.isEmpty()) {
            _status = DELETED
        }
    }

    fun kick(userId: Long, targetUserId: Long) {
        validateGameNotStarted()
        validateHostPermission(userId)
        this._players = _players.remove(_players.findPlayer(targetUserId))
    }

    fun start(userId: Long) {
//        validateHostPermission(userId)
        this._status = GameStatus.PLAYING
    }

    fun updateSubject(userId: Long, subject: GameSubject) {
        validateGameNotStarted()
        validateHostPermission(userId)
        this._setting = _setting.updateSubject(subject)
    }

    fun updateLevel(userId: Long, level: GameLevel) {
        validateGameNotStarted()
        validateHostPermission(userId)
        this._setting = _setting.updateLevel(level)
    }

    fun updateQuizCount(userId: Long, quizCount: Int) {
        validateGameNotStarted()
        validateHostPermission(userId)
        this._setting = _setting.updateQuizCount(quizCount)
    }

    private fun validateGameNotStarted() {
        if (status != WAITING) {
            throw GameAlreadyStartedException
        }
    }

    private fun validatePlayerCount(users: List<User>) {
        if (users.size != 5) {
            throw GameException.GameMisMatchNumberOfPlayersException
        }
    }

    private fun validateHostPermission(userId: Long) {
        val player = players.findPlayer(userId)
        if (player.isGuest()) {
            throw GameHostPermissionException
        }
    }

    fun isReady(): Boolean {
        return _players.isAllParticipated()
    }

}