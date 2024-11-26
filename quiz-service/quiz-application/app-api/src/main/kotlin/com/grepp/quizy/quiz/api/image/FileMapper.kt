package com.grepp.quizy.quiz.api.image

import com.grepp.quizy.quiz.domain.image.ImageFile
import org.springframework.web.multipart.MultipartFile

fun toImageFile(file: MultipartFile): ImageFile {
    val contentType = file.contentType ?: throw IllegalArgumentException("File content type is null")
    return ImageFile(contentType, file.inputStream)
}