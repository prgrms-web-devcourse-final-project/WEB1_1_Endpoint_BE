package com.grepp.quizy.user.domain.user

import com.grepp.quizy.user.domain.game.RatingReader
import com.grepp.quizy.user.domain.quiz.QuizScoreReader
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userAppender: UserAppender,
    private val userReader: UserReader,
    private val userRemover: UserRemover,
    private val userLogoutManager: UserLogoutManager,
    private val userReissueManager: UserReissueManager,
    private val userValidator: UserValidator,
    private val userUpdater: UserUpdater,
    private val ratingReader: RatingReader,
    private val quizScoreReader: QuizScoreReader,

    ) : UserCreateUseCase, UserReadUseCase, UserDeleteUseCase, UserLogoutUseCase, UserReissueUseCase, UserValidUseCase,
    UserUpdateUseCase {

    @Transactional
    override fun appendUser(user: User): User {
        return userAppender.append(user)
    }

    override suspend fun getUserInfo(userId: UserId): UserInfo = coroutineScope {
        // 비동기로 여러 API 동시 호출
        val ratingDeferred = async {
            ratingReader.getRating(userId.value)
        }

        val quizScoreDeferred = async {
            quizScoreReader.getQuizScore(userId.value)
        }

        // 기본 유저 정보는 동기적으로 가져옴 (필수 정보라고 가정)
        val user = userReader.read(userId)

        UserInfo.from(
            user = user,
            userRating = ratingDeferred.await(),
            userQuizScore = quizScoreDeferred.await()
        )
    }

    @Transactional
    override fun removeUser(userId: UserId) {
        userRemover.remove(userId)
    }

    override fun logout(userId: UserId, accessToken: String) {
        userLogoutManager.logout(userId, accessToken)
    }

    override fun reissue(userId: UserId): ReissueToken {
        return userReissueManager.reissue(userId)
    }

    @Transactional
    override fun updateProfile(userId: UserId, name: String?, imageFile: ImageFile?) {
        userUpdater.updateProfile(userId, name, imageFile)
    }

    override fun isValidUser(userId: UserId): Boolean {
        return userValidator.isValid(userId)
    }

    fun changeRole(userId: UserId) {
        userUpdater.updateRole(userId)
    }
}