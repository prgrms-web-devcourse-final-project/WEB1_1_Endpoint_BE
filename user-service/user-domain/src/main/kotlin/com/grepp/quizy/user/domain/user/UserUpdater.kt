package com.grepp.quizy.user.domain.user

import com.grepp.quizy.user.domain.user.exception.CustomUserException
import org.springframework.stereotype.Component

@Component
class UserUpdater(
    private val userRepository: UserRepository
) {
    fun updateProfile(userId: UserId, name: String?, profileImageUrl: String?) {
        val user = userRepository.findById(userId.value) ?: throw CustomUserException.UserNotFoundException
        user.updateProfile(
            UserProfile(
                name = name ?: user.userProfile.name,
                email = user.userProfile.email,
                profileImageUrl = profileImageUrl ?: user.userProfile.profileImageUrl,
            )
        )
        userRepository.save(user)
    }
}