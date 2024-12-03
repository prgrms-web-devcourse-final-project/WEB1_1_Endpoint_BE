package com.grepp.quizy.user.domain.user

import com.grepp.quizy.common.event.EventPublisher
import org.springframework.stereotype.Component

@Component
class UserAppender(
    private val userRepository: UserRepository,
    private val eventPublisher: EventPublisher,
    private val redisTokenRepository: RedisTokenRepository
) {
    fun append(user: User): User {
        val newUser = userRepository.save(user)
        eventPublisher.publish(UserCreatedEvent.from(newUser))
        redisTokenRepository.saveUser(newUser.id)
        return newUser
    }
}
