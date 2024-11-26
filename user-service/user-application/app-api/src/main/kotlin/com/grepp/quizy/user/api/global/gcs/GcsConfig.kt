package com.grepp.quizy.user.api.global.gcs

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.*

@Configuration
class GcsConfig(
    @Value("\${spring.cloud.gcp.storage.credentials.encoded-key}")
    private val gcpKey: String,
    @Value("\${spring.cloud.gcp.storage.project-id}")
    private val projectId: String
) {

    @Bean
    @Throws(IOException::class)
    fun storage(): Storage {
        val credentials: GoogleCredentials = getDecodedCredentials(gcpKey)
        return StorageOptions.newBuilder()
            .setProjectId(projectId)
            .setCredentials(credentials)
            .build()
            .service
    }

    @Throws(IOException::class)
    private fun getDecodedCredentials(encodedKey: String?): GoogleCredentials {
        val decodedKey = Base64.getDecoder().decode(encodedKey)
        return GoogleCredentials.fromStream(ByteArrayInputStream(decodedKey))
    }
}