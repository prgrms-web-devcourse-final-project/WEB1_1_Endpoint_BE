package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.GameStatus.WAITING
import com.grepp.quizy.game.domain.exception.GameException.GameAlreadyStartedException
import com.grepp.quizy.game.domain.exception.GameException.GameHostPermissionException

class Game(
    val id: Long = 0,
    val setting: GameSetting,
    val status: GameStatus = WAITING,
    val players: Players,
    val inviteCode: InviteCode = InviteCode()
) {

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
                setting = GameSetting(subject = subject, level = level, quizCount = quizCount),
                players = Players(listOf(Player(id = userId, role = PlayerRole.HOST)))
            )
            return game
        }
    }

    fun join(userId: Long) {
        validateGameNotStarted()
        players.add(Player(id = userId))
    }

    fun quit(userId: Long) {
        validateGameNotStarted()
        players.remove(Player(id = userId))
    }

    fun updateSubject(userId: Long, subject: GameSubject) {
        validateGameNotStarted()
        validateHostPermission(userId)
        setting.updateSubject(subject)
    }

    fun updateLevel(userId: Long, level: GameLevel) {
        validateGameNotStarted()
        validateHostPermission(userId)
        setting.updateLevel(level)
    }

    fun updateQuizCount(userId: Long, quizCount: Int) {
        validateGameNotStarted()
        validateHostPermission(userId)
        setting.updateQuizCount(quizCount)
    }

    private fun validateHostPermission(userId: Long) {
        val player = players.findPlayerById(userId)
        if (player.isGuest()) {
            throw GameHostPermissionException()
        }
    }

    private fun validateGameNotStarted() {
        if (status != WAITING) {
            throw GameAlreadyStartedException()
        }
    }

}