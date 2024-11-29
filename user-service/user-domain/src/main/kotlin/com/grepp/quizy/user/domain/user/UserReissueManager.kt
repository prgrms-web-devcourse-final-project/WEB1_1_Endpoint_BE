package com.grepp.quizy.user.domain.user

import org.springframework.stereotype.Component

@Component
class UserReissueManager(
    private val userReader: UserReader,
    private val tokenProvider: TokenProvider,
    private val tokenGenerator: TokenGenerator,
    private val redisTokenRepository: RedisTokenRepository
) {
    fun reissue(userId: UserId): ReissueToken {
        val user = userReader.read(userId)


        val accessToken = tokenGenerator.generateAccessToken(user)
        val refreshToken = tokenGenerator.generateRefreshToken(user)
        val accessTokenExpirationTime = tokenProvider.getExpiration(accessToken)
        val refreshTokenExpirationTime = tokenProvider.getExpiration(refreshToken)

        redisTokenRepository.saveSession(user.id, accessTokenExpirationTime)

        return ReissueToken(
            accessToken = accessToken,
            refreshToken = refreshToken,
            refreshTokenExpirationTime = refreshTokenExpirationTime,
            isGuest = user.isGuest()
        )
    }
}