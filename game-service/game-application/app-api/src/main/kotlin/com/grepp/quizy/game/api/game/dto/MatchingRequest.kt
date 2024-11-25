package com.grepp.quizy.game.api.game.dto

import com.grepp.quizy.game.domain.game.GameSubject

data class MatchingRequest(
    val userIds: List<Long>,
    val subject: GameSubject,
) {

}