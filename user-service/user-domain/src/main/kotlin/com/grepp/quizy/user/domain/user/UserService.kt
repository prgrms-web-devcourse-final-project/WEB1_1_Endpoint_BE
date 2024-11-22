package com.grepp.quizy.user.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userAppender: UserAppender,
    private val userReader: UserReader,
    private val userRemover: UserRemover,
    private val userLogoutManager: UserLogoutManager,
    private val userReissueManager: UserReissueManager

) : UserCreateUseCase, UserReadUseCase, UserDeleteUseCase, UserLogoutUseCase, UserReissueUseCase {
    override fun appendUser(user: User): User {
        return userAppender.append(user)
    }

    override fun getUser(userId: UserId): User {
        return userReader.read(userId)
    }

    override fun removeUser(user: User) {
        userRemover.remove(user)
    }

    override fun logout(userId: UserId, accessToken: String) {
        userLogoutManager.logout(userId, accessToken)
    }

    override fun reissue(userId: UserId): ReissueToken {
        return userReissueManager.reissue(userId)
    }


}