package com.grepp.quizy.quiz.domain.image

import java.io.InputStream

data class ImageFile(
    val contentType: String,
    val inputStream: InputStream
) {

}
