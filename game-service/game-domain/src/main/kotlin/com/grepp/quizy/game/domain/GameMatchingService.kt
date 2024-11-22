package com.grepp.quizy.game.domain

import org.springframework.stereotype.Service

@Service
class GameMatchingService(
    private val gameAppender: GameAppender,
) {

    fun create(userIds: List<Long>, subject: GameSubject): Game {
        return gameAppender.appendRandomGame(
            userIds = userIds,
            subject = subject
        )
    }

}