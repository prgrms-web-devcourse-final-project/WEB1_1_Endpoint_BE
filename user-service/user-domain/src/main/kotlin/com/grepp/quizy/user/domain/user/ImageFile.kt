package com.grepp.quizy.user.domain.user

data class ImageFile(
    val contentType: String,
    val bytes: ByteArray,
    val originalFilename: String?,
    val size: Long
) {


}