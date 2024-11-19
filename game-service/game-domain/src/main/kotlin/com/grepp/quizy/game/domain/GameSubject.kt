package com.grepp.quizy.game.domain

enum class GameSubject(
    val description: String
) {
    JAVASCRIPT("자바스크립트"),
    SPRING("스프링"),
    // TODO: 정해진 형식의 주제 10개를 Enum으로 정의한다.
    ;
}