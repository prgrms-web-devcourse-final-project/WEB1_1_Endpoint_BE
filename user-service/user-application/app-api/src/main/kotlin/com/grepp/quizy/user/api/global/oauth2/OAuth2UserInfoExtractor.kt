package com.grepp.quizy.user.api.global.oauth2

interface OAuth2UserInfoExtractor {
    fun extract(attributes: Map<String, Any>): OAuth2Attributes
}