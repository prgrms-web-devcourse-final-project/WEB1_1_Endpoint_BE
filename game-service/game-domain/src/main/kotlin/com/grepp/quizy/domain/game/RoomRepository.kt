package com.grepp.quizy.domain.game

interface RoomRepository {

    fun save(room: Room): Room

}