package com.grepp.quizy.user.api.global.oauth2

import com.grepp.quizy.domain.user.AuthProvider
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomOAuth2User(
    private val oauth2Attributes: OAuth2Attributes
) : OAuth2User {
    override fun getName(): String = oauth2Attributes.name

    override fun getAttributes(): Map<String, Any> = oauth2Attributes.getAttributes()

    override fun getAuthorities(): Collection<GrantedAuthority> =
        oauth2Attributes.getAuthorities()

    // OAuth2Attributes의 다른 정보들에 접근하기 위한 메소드들
    fun getEmail(): String = oauth2Attributes.email
    fun getProvider(): AuthProvider = oauth2Attributes.provider
    fun getSnsId(): String = oauth2Attributes.snsId
}