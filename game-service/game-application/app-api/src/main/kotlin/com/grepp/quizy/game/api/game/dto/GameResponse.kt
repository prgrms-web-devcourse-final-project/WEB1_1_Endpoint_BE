package com.grepp.quizy.game.api.game.dto

import com.grepp.quizy.game.domain.Game

class GameResponse(
    private val id: Long,
    private val inviteCode: String
) {
    companion object {
        fun from(game: Game): GameResponse {
            return GameResponse(
                id = game.id,
                inviteCode = game.inviteCode
            )
        }
    }
}