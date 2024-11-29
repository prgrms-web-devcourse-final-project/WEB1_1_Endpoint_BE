package com.grepp.quizy.matching.infra.game.client

import com.grepp.quizy.matching.infra.feign.config.FeignConfig
import com.grepp.quizy.matching.infra.game.dto.GameRoomIdRequest
import com.grepp.quizy.matching.infra.game.dto.GameRoomIdResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "game-room-client",
    url = "\${feign.client.url.game-service}",
    configuration = [FeignConfig::class]
)
interface GameRoomClient {

    @PostMapping("/api/game/matching")
    fun getGameRoomId(@RequestBody request: GameRoomIdRequest): GameRoomIdResponse
}
