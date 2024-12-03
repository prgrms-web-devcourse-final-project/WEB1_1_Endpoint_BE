package com.grepp.quizy.user.domain.user

import com.grepp.quizy.user.domain.user.exception.CustomUserException
import org.springframework.stereotype.Component

@Component
class UserUpdater(
    private val userRepository: UserRepository,
    private val userMessageSender: UserMessageSender,
    private val profileImageUploader: ProfileImageUploader,
) {
    fun updateProfile(userId: UserId, name: String?, imageFile: ImageFile?) {
        if (name == null && imageFile == null) {
            throw IllegalArgumentException("변경 할 프로필 정보가 없습니다.")
        }

        val profileImageUrl = imageFile?.let { profileImageUploader.uploadFile(it, "profiles") }

        val user = userRepository.findById(userId.value) ?: throw CustomUserException.UserNotFoundException
        user.updateProfile(
            UserProfile(
                name = name ?: user.userProfile.name,
                email = user.userProfile.email,
                profileImageUrl = profileImageUrl ?: user.userProfile.profileImageUrl,
            )
        )
        userRepository.save(user)
//        userMessageSender.send(UpdateUserEvent.from(user))
    }

    fun updateRole(userId: UserId) {
        val user = userRepository.findById(userId.value) ?: throw CustomUserException.UserNotFoundException
        user.changeRole()
        userRepository.save(user)
    }
}