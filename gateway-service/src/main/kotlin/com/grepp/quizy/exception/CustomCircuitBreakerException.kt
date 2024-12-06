package com.grepp.quizy.exception

import com.grepp.quizy.common.exception.WebException

sealed class CustomCircuitBreakerException(errorCode: CircuitBreakerErrorCode) : WebException(errorCode) {
    data object GameServiceUnavailableException :
        CustomCircuitBreakerException(CircuitBreakerErrorCode.GAME_UNAVAILABLE) {
        private fun readResolve(): Any = GameServiceUnavailableException

        val EXCEPTION: CustomCircuitBreakerException = GameServiceUnavailableException
    }

    data object UserServiceUnavailableException :
        CustomCircuitBreakerException(CircuitBreakerErrorCode.USER_UNAVAILABLE) {
        private fun readResolve(): Any = UserServiceUnavailableException

        val EXCEPTION: CustomCircuitBreakerException = UserServiceUnavailableException
    }

    data object QuizServiceUnavailableException :
        CustomCircuitBreakerException(CircuitBreakerErrorCode.QUIZ_UNAVAILABLE) {
        private fun readResolve(): Any = QuizServiceUnavailableException

        val EXCEPTION: CustomCircuitBreakerException = QuizServiceUnavailableException
    }

    data object MatchingServiceUnavailableException :
        CustomCircuitBreakerException(CircuitBreakerErrorCode.MATCHING_UNAVAILABLE) {
        private fun readResolve(): Any = MatchingServiceUnavailableException

        val EXCEPTION: CustomCircuitBreakerException = MatchingServiceUnavailableException
    }

    data object WsUnavailableException :
        CustomCircuitBreakerException(CircuitBreakerErrorCode.WS_UNAVAILABLE) {
        private fun readResolve(): Any = WsUnavailableException

        val EXCEPTION: CustomCircuitBreakerException = WsUnavailableException
    }
}


