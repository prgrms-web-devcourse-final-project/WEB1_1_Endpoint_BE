package com.grepp.quizy.game.domain.exception

import com.grepp.quizy.common.exception.DomainException

sealed class GameException (
    errorCode: GameErrorCode,
) : DomainException(errorCode) {

    class GameNotFoundException : GameException(GameErrorCode.GAME_NOT_FOUND)
    class GameAlreadyParticipatedException : GameException(GameErrorCode.GAME_ALREADY_PARTICIPATED)
    class GameAlreadyStartedException : GameException(GameErrorCode.GAME_ALREADY_STARTED)
    class GameAlreadyFullException : GameException(GameErrorCode.GAME_PARTICIPANT_ALREADY_FULL)
    class GameNotParticipatedException : GameException(GameErrorCode.GAME_NOT_PARTICIPATED)
}