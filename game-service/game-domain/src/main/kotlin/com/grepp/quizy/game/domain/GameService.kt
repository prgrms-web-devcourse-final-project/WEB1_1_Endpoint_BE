package com.grepp.quizy.game.domain

import org.springframework.stereotype.Service

@Service
class GameService(
    private val gameAppender: GameAppender,
    private val gameReader: GameReader,
    private val gamePlayerManager: GamePlayerManager,
    private val gameSettingManager: GameSettingManager,
    private val messagePublisher: GameMessagePublisher
) {

    fun create(
        userId: Long,
        subject: GameSubject,
        level: GameLevel,
        quizCount: Int
    ): Game {
        return gameAppender.append(
            userId = userId,
            subject = subject,
            level = level,
            quizCount = quizCount
        )
    }

    fun join(userId: Long, code: String): Game {
        val game = gameReader.readByInviteCode(code)
        val currentGame = gamePlayerManager.join(game, userId)
        // TODO: publish message
        return currentGame
    }

    fun quit(userId: Long, code: String) {
        val game = gameReader.readByInviteCode(code)
        val currentGame = gamePlayerManager.quit(game, userId)
        // TODO: publish message
    }

    fun updateSubject(userId: Long, gameId: Long, subject: GameSubject) {
        val game = gameReader.read(gameId)
        val currentGame = gameSettingManager.updateSubject(game, subject, userId)
        // TODO: publish message
    }

    fun updateLevel(userId: Long, gameId: Long, level: GameLevel) {
        val game = gameReader.read(gameId)
        val currentGame = gameSettingManager.updateLevel(game, level, userId)
        // TODO: publish message
    }

    fun updateQuizCount(userId: Long, gameId: Long, quizCount: Int) {
        val game = gameReader.read(userId)
        val currentGame = gameSettingManager.updateQuizCount(game, quizCount, userId)
        // TODO: publish message
    }

    fun kickUser(userId: Long, gameId: Long, targetUserId: Long) {
        val game = gameReader.read(gameId)
        val currentGame = gamePlayerManager.kick(game, userId, targetUserId)
        TODO("publish message / 메시지 보낼 형식이 정해지면 메시지 보낼 것")
    }

}