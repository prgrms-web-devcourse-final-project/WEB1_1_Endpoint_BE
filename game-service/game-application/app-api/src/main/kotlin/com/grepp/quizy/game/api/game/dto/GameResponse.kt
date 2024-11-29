package com.grepp.quizy.game.api.game.dto

import com.grepp.quizy.game.domain.game.Game

data class GameResponse(
    val id: Long,
    val inviteCode: String?,
) {
    companion object {
        fun from(game: Game): GameResponse {
            return GameResponse(
                id = game.id,
                inviteCode = game.inviteCode?.value,
            )
        }
    }
}
