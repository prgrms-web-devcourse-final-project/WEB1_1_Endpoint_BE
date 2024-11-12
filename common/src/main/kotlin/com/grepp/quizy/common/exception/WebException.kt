package com.grepp.quizy.common.exception

open class WebException(errorCode: BaseErrorCode) :
        CustomException(errorCode, "웹 계층 예외")
