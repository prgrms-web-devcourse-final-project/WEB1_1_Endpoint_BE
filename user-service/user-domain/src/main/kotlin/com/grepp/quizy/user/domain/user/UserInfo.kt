package com.grepp.quizy.user.domain.user

import com.grepp.quizy.user.domain.game.UserRating
import com.grepp.quizy.user.domain.quiz.UserQuizScore

data class UserInfo(
    val id: Long,
    val email: String,
    val name: String,
    val profileImageUrl: String,
    val provider: AuthProvider,
    val role: String,
    val rating: Int?,
    val solvedProblems: Int?,
    val correctAnswerRate: Double?,
    val achievements: List<String>?
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
                solvedProblems = userQuizScore?.solvedProblems,
                correctAnswerRate = userQuizScore?.correctAnswerRate,
                achievements = userQuizScore?.achievements
            )
        }
    }
}
