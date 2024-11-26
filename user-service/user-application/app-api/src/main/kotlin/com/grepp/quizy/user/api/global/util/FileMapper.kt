package com.grepp.quizy.user.api.global.util

import com.grepp.quizy.user.domain.user.ImageFile
import org.springframework.web.multipart.MultipartFile

fun toImageFile(file: MultipartFile): ImageFile {
    val contentType = file.contentType ?: throw IllegalArgumentException("File content type is null")
    return ImageFile(contentType, file.bytes, file.originalFilename, file.size)
}