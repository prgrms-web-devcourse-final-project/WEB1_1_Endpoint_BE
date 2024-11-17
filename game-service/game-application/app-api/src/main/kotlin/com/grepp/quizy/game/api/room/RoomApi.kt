package com.grepp.quizy.game.api.room

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.game.domain.Room
import com.grepp.quizy.game.domain.RoomService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/room")
class RoomApi(
    private val roomService: RoomService
) {

    @PostMapping
    fun createRoom(): ApiResponse<Room> {
        return ApiResponse.success(roomService.createRoom())
    }

}