package com.grepp.quizy.game.domain

enum class GameStatus(
    val description: String
) {
    WAITING("대기 중"),
    PLAYING("진행 중"),
    FINISHED("종료"),
    DELETED("삭제됨")

    ;

}
