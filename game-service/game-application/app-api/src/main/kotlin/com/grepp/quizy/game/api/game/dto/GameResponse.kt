package com.grepp.quizy.game.api.game.dto

import com.grepp.quizy.game.domain.game.Game
import com.grepp.quizy.game.domain.game.Player

data class GameResponse(
    val id: Long,
    val subject: String,
    val level: String,
    val quizCount: Int,
    val players: List<UserInfoResponse>,
    val inviteCode: String?,
) {
    companion object {
        fun from(game: Game): GameResponse {
            return GameResponse(
                id = game.id,
                subject = game.setting.subject.description,
                level = game.setting.level.description,
                quizCount = game.setting.quizCount,
                players = game.players.players.map { UserInfoResponse.from(it) },
                inviteCode = game.inviteCode?.value,
            )
        }
    }
}

data class UserInfoResponse(
    val id: Long,
    val name: String,
    val imgPath: String,
    val rating: Int,
    val role: String,
    val host: Boolean,
    val score: Int = 0
) {
    companion object {
        fun from(player: Player): UserInfoResponse {
            return UserInfoResponse(
                id = player.user.id,
                name = player.user.name,
                imgPath = player.user.imgPath,
                rating = player.user.rating,
                host = player.isHost(),
                role = player.role.name
            )
        }
    }
}