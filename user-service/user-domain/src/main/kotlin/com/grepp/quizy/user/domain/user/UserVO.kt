package com.grepp.quizy.user.domain.user

@JvmInline
value class UserId(val value: Long)

data class UserProfile(
    val name: String,
    val email: String,
    val profileImageUrl: String = "https://storage.googleapis.com/quizy_bucket-1/profiles/Default-Profile-Picture-Download-PNG-Image.png"
) {
}

data class ProviderType(
    val provider: AuthProvider,
    val providerId: String
) {
}

enum class AuthProvider {
    GOOGLE,
    KAKAO,
    DEFAULT
}