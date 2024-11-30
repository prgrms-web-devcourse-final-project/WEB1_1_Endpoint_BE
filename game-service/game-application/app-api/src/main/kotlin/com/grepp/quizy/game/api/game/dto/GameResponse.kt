package com.grepp.quizy.game.api.game.dto

import com.grepp.quizy.game.domain.game.Game
import com.grepp.quizy.game.domain.game.Player

data class GameResponse(
    val id: Long,
    val subject: String,
    val level: String,
    val quizCount: Int,
    val players: List<Player>,
    val inviteCode: String?,
) {
    companion object {
        fun from(game: Game): GameResponse {
            return GameResponse(
                id = game.id,
                subject = game.setting.subject.description,
                level = game.setting.level.description,
                quizCount = game.setting.quizCount,
                players = game.players.players,
                inviteCode = game.inviteCode?.value,
            )
        }
    }
}
