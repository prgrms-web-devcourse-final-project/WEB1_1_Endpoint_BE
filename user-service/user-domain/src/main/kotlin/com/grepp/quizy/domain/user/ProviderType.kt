package com.grepp.quizy.domain.user

data class ProviderType(
        val provider: AuthProvider,
        val providerId: String,
) {}
