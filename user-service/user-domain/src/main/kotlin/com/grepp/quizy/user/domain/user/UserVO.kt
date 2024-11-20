package com.grepp.quizy.user.domain.user

@JvmInline
value class UserId(val value: Long)

data class UserProfile(
    val name: String,
    val email: String,
    val profileImageUrl: String = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" // TODO : S3 기본 썸네일 등록하기
) {
}

data class ProviderType(
    val provider: AuthProvider,
    val providerId: String
) {
}

enum class AuthProvider {
    GOOGLE,
    KAKAO
}