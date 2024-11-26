package com.grepp.quizy.quiz.infra.image

import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.grepp.quizy.quiz.domain.image.ImageFile
import com.grepp.quizy.quiz.domain.image.QuizImage
import com.grepp.quizy.quiz.domain.image.QuizImageUploader
import com.grepp.quizy.quiz.infra.image.exception.QuizImageInfraException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.IOException
import java.nio.channels.Channels
import java.util.*

private val log = KotlinLogging.logger {}

@Component
class FileGcsUploader(
    private val storage: Storage,
    @Value("\${spring.cloud.gcp.storage.bucket}")
    private val bucketName: String,
    @Value("\${spring.cloud.gcp.storage.url}")
    private val storagePath: String
) : QuizImageUploader {
    override fun upload(imageFile: ImageFile): QuizImage {
        val uuid = UUID.randomUUID().toString()
        val blobInfo: BlobInfo =
            BlobInfo.newBuilder(bucketName, uuid).setContentType(imageFile.contentType).build()
        try {
            storage.writer(blobInfo).use { writer ->
                imageFile.inputStream.use { input ->
                    input.transferTo(Channels.newOutputStream(writer))
                    log.info { "이미지 업로드 성공 - ${storagePath + uuid}" }
                    return QuizImage.from(storagePath + uuid)
                }
            }
        } catch (e: IOException) {
            log.error { "이미지 업로드 실패" }
            throw QuizImageInfraException.UploadError
        }
    }
}