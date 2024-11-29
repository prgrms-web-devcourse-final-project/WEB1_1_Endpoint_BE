package com.grepp.quizy.matching.infra.game.dto

data class GameRoomIdRequest(val userIds: List<Long>, val subject: String)