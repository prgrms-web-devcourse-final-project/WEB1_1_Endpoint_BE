package com.grepp.quizy.game.domain

interface RoomRepository {

    fun save(room: Room): Room

    fun findById(id: Long): Room?

}