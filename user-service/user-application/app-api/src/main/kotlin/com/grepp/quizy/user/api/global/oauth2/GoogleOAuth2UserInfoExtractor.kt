package com.grepp.quizy.user.api.global.oauth2

import com.grepp.quizy.domain.user.AuthProvider

class GoogleOAuth2UserInfoExtractor : OAuth2UserInfoExtractor {
    override fun extract(attributes: Map<String, Any>): OAuth2Attributes {
        return OAuth2Attributes(
            name = attributes["name"] as String,
            email = attributes["email"] as String,
            provider = AuthProvider.GOOGLE,
            snsId = attributes["sub"] as String,
            attributes = attributes
        )
    }
}