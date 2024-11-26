package com.grepp.quizy.user.infra.gcs

import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.grepp.quizy.user.domain.user.ImageFile
import com.grepp.quizy.user.domain.user.ProfileImageUploader
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class GcsFileUploader(
    private val storage: Storage,
    @Value("\${spring.cloud.gcp.storage.bucket}")
    private val bucketName: String,
    @Value("\${spring.cloud.gcp.storage.url}")
    private val storagePath: String
) : ProfileImageUploader {
    override fun uploadFile(file: ImageFile, directory: String): String {
        validateProfileImage(file)

        val fileName = generateFileName(file.originalFilename)
        val blobInfo = BlobInfo.newBuilder(bucketName, "$directory/$fileName")
            .setContentType(file.contentType)
            .build()

        storage.create(blobInfo, file.bytes)

        return "$storagePath$directory/$fileName"
    }

    private fun generateFileName(originalFileName: String?): String {
        val extension = originalFileName?.substringAfterLast('.', "")
        return "${UUID.randomUUID()}.$extension"
    }

    private fun validateProfileImage(file: ImageFile) {
        // 파일 크기 검증 (예: 5MB 제한)
        if (file.size > 5 * 1024 * 1024) {
            throw IllegalArgumentException("프로필 이미지는 5MB를 초과할 수 없습니다.")
        }

        // 파일 형식 검증
        val allowedContentTypes = listOf("image/jpeg", "image/png", "image/gif")
        if (!allowedContentTypes.contains(file.contentType)) {
            throw IllegalArgumentException("지원하지 않는 이미지 형식입니다. (지원 형식: JPEG, PNG, GIF)")
        }
    }
}