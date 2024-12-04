package com.grepp.quizy.user.domain.user

import com.grepp.quizy.user.domain.game.UserRating
import com.grepp.quizy.user.domain.quiz.Achievement
import com.grepp.quizy.user.domain.quiz.UserQuizScore

data class UserInfo(
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
        fun from(
            user: User, userRating: UserRating?, userQuizScore: UserQuizScore?
        ): UserInfo {
            return UserInfo(
                id = user.id.value,
                email = user.userProfile.email,
                name = user.userProfile.name,
                profileImageUrl = user.userProfile.profileImageUrl,
                provider = user.provider.provider,
                role = user.role.name,
                rating = userRating?.rating,
                totalAnswered = userQuizScore?.totalAnswered,
                correctRate = userQuizScore?.correctRate,
                achievements = userQuizScore?.achievements
            )
        }
    }
}
