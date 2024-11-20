package com.grepp.quizy.game.domain.exception

import com.grepp.quizy.common.exception.DomainException

sealed class GameException (
    errorCode: GameErrorCode,
) : DomainException(errorCode) {

    data object GameNotFoundException : GameException(GameErrorCode.GAME_NOT_FOUND)
    data object GameAlreadyParticipatedException : GameException(GameErrorCode.GAME_ALREADY_PARTICIPATED)
    data object GameAlreadyStartedException : GameException(GameErrorCode.GAME_ALREADY_STARTED)
    data object GameAlreadyFullException : GameException(GameErrorCode.GAME_PARTICIPANT_ALREADY_FULL)
    data object GameNotParticipatedException : GameException(GameErrorCode.GAME_NOT_PARTICIPATED)

}