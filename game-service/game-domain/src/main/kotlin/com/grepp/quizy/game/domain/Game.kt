package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.GameStatus.WAITING
import com.grepp.quizy.game.domain.exception.GameException.*
import java.security.SecureRandom

class Game(
    val id: Long = 0,
    val subject: GameSubject,
    val quizCount: Int,
    val level: GameLevel,
    val status: GameStatus = WAITING,
    val playerIds: MutableSet<Long> = mutableSetOf(),
    val inviteCode: String = generateInviteCode()
) {

    companion object {

        private const val INVITE_CODE_LENGTH = 6
        private val ALLOWED_CHARS = ('A'..'Z') + ('0'..'9')
        private val secureRandom = SecureRandom()

        private fun generateInviteCode(): String = buildString {
            repeat(INVITE_CODE_LENGTH) {
                append(ALLOWED_CHARS[secureRandom.nextInt(ALLOWED_CHARS.size)])
            }
        }

        fun create(
            id: Long,
            subject: GameSubject,
            quizCount: Int,
            level: GameLevel,
            userId: Long
        ): Game {
            val game = Game(id = id, subject = subject, level = level, quizCount = quizCount)
            game.join(userId)
            return game
        }
    }

    fun join(userId: Long) {
        checkJoinable(userId)
        playerIds.add(userId)
    }

    fun quit(userId: Long) {
        checkQuitable(userId)
        playerIds.remove(userId)
    }

    private fun checkQuitable(userId: Long) {
        validatePlayerAlreadyJoined(userId)
        validateGameIsWaiting()
    }

    private fun checkJoinable(userId: Long) {
        validatePlayerNotAlreadyJoined(userId)
        validateGameIsWaiting()
        validateGameHasCapacity()
    }

    private fun validatePlayerAlreadyJoined(userId: Long) {
        if (!playerIds.contains(userId)) {
            throw GameNotParticipatedException
        }
    }

    private fun validatePlayerNotAlreadyJoined(userId: Long) {
        if (playerIds.contains(userId)) {
            throw GameAlreadyParticipatedException
        }
    }

    private fun validateGameIsWaiting() {
        if (status != WAITING) {
            throw GameAlreadyStartedException
        }
    }

    private fun validateGameHasCapacity() {
        if (playerIds.size >= 5) {
            throw GameAlreadyFullException
        }
    }

}