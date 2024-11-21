package com.grepp.quizy.game.infra.websocket

enum class WebSocketDestination(val destination: String) {

    SINGLE_PREFIX("/queue"),
    MULTIPLE_PREFIX("/topic"),
    USER_PREFIX("/user"),
    APPLICATION_PREFIX("/app"),
    QUIZ_GRADE("/quiz-grade"),
}
