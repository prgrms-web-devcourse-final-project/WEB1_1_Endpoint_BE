package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.GameStatus.DELETED
import com.grepp.quizy.game.domain.GameStatus.WAITING
import com.grepp.quizy.game.domain.exception.GameException.GameAlreadyStartedException
import com.grepp.quizy.game.domain.exception.GameException.GameHostPermissionException

class Game(
    val id: Long = 0,
    private var _setting: GameSetting,
    private var _status: GameStatus = WAITING,
    private var _players: Players,
    val inviteCode: InviteCode = InviteCode()
) {
    val setting: GameSetting
        get() = _setting

    val players: Players
        get() = _players

    val status: GameStatus
        get() = _status

    companion object {
        fun create(
            id: Long,
            subject: GameSubject,
            quizCount: Int,
            level: GameLevel,
            userId: Long
        ): Game {
            val game = Game(
                id = id,
                _setting = GameSetting(subject = subject, level = level, quizCount = quizCount),
                _players = Players(listOf(Player(id = userId, _role = PlayerRole.HOST)))
            )
            return game
        }
    }

    fun join(userId: Long) {
        validateGameNotStarted()
        this._players = _players.add(Player(id = userId))
    }

    fun quit(userId: Long) {
        val player = _players.findPlayerById(userId)
        this._players = _players.remove(player)
        if(_players.isEmpty()) {
            _status = DELETED
        }
    }

    fun kick(userId: Long, targetId: Long) {
        validateGameNotStarted()
        validateHostPermission(userId)
        this._players = _players.remove(Player(id = targetId))
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

    private fun validateHostPermission(userId: Long) {
        val player = players.findPlayerById(userId)
        if (player.isGuest()) {
            throw GameHostPermissionException
        }
    }

    private fun validateGameNotStarted() {
        if (status != WAITING) {
            throw GameAlreadyStartedException
        }
    }

}