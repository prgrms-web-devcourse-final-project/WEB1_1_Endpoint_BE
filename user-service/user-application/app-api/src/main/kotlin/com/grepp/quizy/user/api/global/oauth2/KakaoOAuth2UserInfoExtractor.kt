package com.grepp.quizy.user.api.global.oauth2

import com.grepp.quizy.user.domain.user.AuthProvider

class KakaoOAuth2UserInfoExtractor : OAuth2UserInfoExtractor {
    override fun extract(attributes: Map<String, Any>): OAuth2Attributes {
        val kakaoAccount = attributes["kakao_account"] as Map<String, Any>
        val profile = kakaoAccount["profile"] as Map<String, Any>

        return OAuth2Attributes(
            name = profile["nickname"] as String,
            email = kakaoAccount["email"] as String,
            provider = AuthProvider.KAKAO,
            snsId = attributes["id"].toString(),
            profileImageUrl = profile["profile_image_url"] as String,
            attributes = attributes
        )
    }
}