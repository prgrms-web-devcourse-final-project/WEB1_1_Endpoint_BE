package com.grepp.quizy.user.api.global.oauth2

import org.springframework.stereotype.Component

@Component
class OAuth2UserInfoExtractorFactory {
    private val extractors = mapOf(
        "google" to GoogleOAuth2UserInfoExtractor(),
        "kakao" to KakaoOAuth2UserInfoExtractor()
    )

    fun getExtractor(registrationId: String): OAuth2UserInfoExtractor {
        return extractors[registrationId]
            ?: throw IllegalArgumentException("Unsupported OAuth2 provider: $registrationId")
        // TODO: custom 예외 추가
    }
}