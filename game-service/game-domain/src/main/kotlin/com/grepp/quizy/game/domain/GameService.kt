package com.grepp.quizy.game.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GameService(
    private val gameAppender: GameAppender,
    private val gameReader: GameReader,
    private val gamePlayerManager: GamePlayerManager,
    private val gameSettingManager: GameSettingManager,
    private val messagePublisher: GameMessagePublisher
) {

    @Transactional
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

    @Transactional
    fun join(userId: Long, code: String): Game {
        val game = gameReader.readByInviteCode(code)
        val currentGame = gamePlayerManager.join(game, userId)
        // TODO: publish message
        return currentGame
    }

    @Transactional
    fun quit(userId: Long, code: String) {
        val game = gameReader.readByInviteCode(code)
        val currentGame = gamePlayerManager.quit(game, userId)
        // TODO: publish message
    }

    @Transactional
    fun updateSubject(userId: Long, gameId: Long, subject: GameSubject) {
        val game = gameReader.read(gameId)
        val currentGame = gameSettingManager.updateSubject(game, subject, userId)
        // TODO: publish message
    }

    @Transactional
    fun updateLevel(userId: Long, gameId: Long, level: GameLevel) {
        val game = gameReader.read(gameId)
        val currentGame = gameSettingManager.updateLevel(game, level, userId)
        // TODO: publish message
    }

    @Transactional
    fun updateQuizCount(userId: Long, gameId: Long, quizCount: Int) {
        val game = gameReader.read(userId)
        val currentGame = gameSettingManager.updateQuizCount(game, quizCount, userId)
        // TODO: publish message
    }

}