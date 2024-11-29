package com.grepp.quizy.matching.infra.game.dto

data class GameRoomIdResponse(
    val id: Long,
    val inviteCode: String?,
)