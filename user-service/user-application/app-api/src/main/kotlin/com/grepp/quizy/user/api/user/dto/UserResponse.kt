package com.grepp.quizy.user.api.user.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.grepp.quizy.user.domain.quiz.Achievement
import com.grepp.quizy.user.domain.user.AuthProvider
import com.grepp.quizy.user.domain.user.UserInfo

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserResponse(
    val id: Long,
    val email: String,
    val name: String,
    val profileImageUrl: String,
    val provider: AuthProvider,
    val role: String,
    val rating: Int?,
    val totalAnswered: Int?,
    val correctRate: Double?,
    val achievements: List<Achievement>?
) {
    companion object {
        fun from(userInfo: UserInfo): UserResponse {
            return UserResponse(
                id = userInfo.id,
                email = userInfo.email,
                name = userInfo.name,
                profileImageUrl = userInfo.profileImageUrl,
                provider = userInfo.provider,
                role = userInfo.role,
                rating = userInfo.rating,
                totalAnswered = userInfo.totalAnswered,
                correctRate = userInfo.correctRate,
                achievements = userInfo.achievements
            )
        }
    }
}