package com.grepp.quizy.game.domain.exception

import com.grepp.quizy.common.exception.BaseErrorCode
import com.grepp.quizy.common.exception.ErrorReason

enum class GameErrorCode(
    private val status: Int,
    private val errorCode: String,
    private val message: String,
) : BaseErrorCode {

    GAME_NOT_FOUND(404, "G001", "해당 게임을 찾지 못했습니다."),
    GAME_ALREADY_STARTED(409, "G002", "이미 시작된 게임입니다."),
    GAME_ALREADY_FINISHED(409, "G003", "이미 종료된 게임입니다."),
    GAME_NOT_STARTED(409, "G004", "게임이 시작되지 않았습니다."),
    GAME_NOT_ENOUGH_PLAYER(409, "G005", "게임 참가자가 부족합니다."),
    GAME_NOT_YOUR_TURN(409, "G006", "당신의 차례가 아닙니다."),
    GAME_ALREADY_ANSWERED(409, "G007", "이미 정답을 제출했습니다."),
    GAME_INVALID_ANSWER(409, "G008", "유효하지 않은 정답입니다."),
    GAME_INVALID_STATUS(409, "G009", "유효하지 않은 게임 상태입니다."),
    GAME_INVALID_INDEX(409, "G010", "유효하지 않은 문제 번호입니다."),
    GAME_INVALID_PLAYER(409, "G011", "유효하지 않은 참가자입니다."),
    GAME_INVALID_PLAYER_COUNT(409, "G012", "유효하지 않은 참가자 수입니다."),
    GAME_INVALID_PLAYER_STATUS(409, "G013", "유효하지 않은 참가자 상태입니다."),
    GAME_INVALID_PLAYER_ANSWER(409, "G014", "유효하지 않은 참가자 답변입니다."),
    GAME_INVALID_PLAYER_SCORE(409, "G015", "유효하지 않은 참가자 점수입니다."),
    GAME_INVALID_PLAYER_RANK(409, "G016", "유효하지 않은 참가자 순위입니다."),
    GAME_INVALID_PLAYER_RANKING(409, "G017", "유효하지 않은 참가자 순위 목록입니다."),
    GAME_INVALID_PLAYER_RANKING_SIZE(
        409,
        "G018",
        "유효하지 않은 참가자 순위 목록 크기입니다.",
    ),
    GAME_INVALID_PLAYER_RANKING_SCORE(
        409,
        "G019",
        "유효하지 않은 참가자 순위 목록 점수입니다.",
    ),
    GAME_ALREADY_PARTICIPATED(409, "G020", "이미 참가한 게임입니다."),
    GAME_PARTICIPANT_ALREADY_FULL(409, "G021", "게임 대기 인원이 꽉 찼습니다."),
    GAME_NOT_PARTICIPATED(409, "G022", "참가하지 않은 게임입니다."),
    GAME_HOST_PERMISSION(409, "G023", "게임 방장만 가능한 작업입니다."),
    GAME_MISMATCH_NUMBER_OF_PLAYERS(409, "G024", "참가자 수가 일치하지 않습니다."),
    GAME_SUBJECT_NOT_FOUND(409, "G025", "해당 주제를 찾지 못했습니다."),
    GAME_LEVEL_NOT_FOUND(409, "G026", "해당 난이도를 찾지 못했습니다."),

    ;

    override val errorReason: ErrorReason
        get() = ErrorReason.of(status, errorCode, message)
}
