package com.grepp.quizy.game.infra.feign

import feign.Response
import feign.codec.ErrorDecoder

class FeignErrorDecoder : ErrorDecoder {

    override fun decode(p0: String?, p1: Response?): Exception {
        return when(p1?.status()) {
            400 -> IllegalArgumentException("Bad Request")
            404 -> IllegalArgumentException("Not Found")
            500 -> IllegalArgumentException("Internal Server Error")
            else -> IllegalArgumentException("Unknown Error")
        }
    }
}