package com.grepp.quizy.game.domain.exception

import com.grepp.quizy.common.exception.DomainException

sealed class GameException(errorCode: GameErrorCode) :
    DomainException(errorCode) {

    data object GameNotFoundException :
        GameException(GameErrorCode.GAME_NOT_FOUND)

    data object GameAlreadyParticipatedException :
        GameException(GameErrorCode.GAME_ALREADY_PARTICIPATED)

    data object GameAlreadyStartedException :
        GameException(GameErrorCode.GAME_ALREADY_STARTED)

    data object GameAlreadyFullException :
        GameException(
            GameErrorCode.GAME_PARTICIPANT_ALREADY_FULL
        )

    data object GameNotParticipatedException :
        GameException(GameErrorCode.GAME_NOT_PARTICIPATED)

    data object GameHostPermissionException :
        GameException(GameErrorCode.GAME_HOST_PERMISSION)

    data object GameMisMatchNumberOfPlayersException :
        GameException(GameErrorCode.GAME_MISMATCH_NUMBER_OF_PLAYERS)

    data object GameSubjectNotFoundException :
        GameException(GameErrorCode.GAME_SUBJECT_NOT_FOUND)

    data object GameLevelNotFoundException :
        GameException(GameErrorCode.GAME_LEVEL_NOT_FOUND)
}
