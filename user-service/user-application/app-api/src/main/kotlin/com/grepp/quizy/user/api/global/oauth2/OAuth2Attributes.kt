package com.grepp.quizy.user.api.global.oauth2

import com.grepp.quizy.user.domain.user.AuthProvider
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

// OAuth2User를 구현하지 않고 별도의 데이터 클래스로 변경
data class OAuth2Attributes(
    val name: String,
    val email: String,
    val provider: AuthProvider,
    val snsId: String,
    val profileImageUrl: String,
    private val attributes: Map<String, Any>
) {
    // OAuth2User의 메소드들은 별도로 구현하지 않음
    fun getAttributes(): Map<String, Any> = attributes

    fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(SimpleGrantedAuthority("ROLE_USER"))
}