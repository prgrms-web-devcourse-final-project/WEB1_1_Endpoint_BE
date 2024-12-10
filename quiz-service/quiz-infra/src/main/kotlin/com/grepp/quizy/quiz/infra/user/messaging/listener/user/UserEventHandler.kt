package com.grepp.quizy.quiz.infra.user.messaging.listener.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.quiz.domain.user.QuizUserService
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.infra.user.messaging.listener.AbstractEventHandler
import com.grepp.quizy.quiz.infra.user.messaging.listener.EventHandler
import com.grepp.quizy.quiz.infra.user.messaging.listener.getPayloadAs
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class UserEventHandler(
    mapper: ObjectMapper,
    private val quizUserService: QuizUserService
) : AbstractEventHandler<UserEvent>(mapper), EventHandler {
    init {
        initActions()
    }

    final override fun initActions() {
        actions.put(UserEvent.UserEventType.USER_CREATED) { event ->
            val userCreatedEvent = event.getPayloadAs<UserCreatedEvent>()
            log.info { "User Created Event: $userCreatedEvent" }
            quizUserService.createUser(UserId(userCreatedEvent.userId), userCreatedEvent.toProfile())
        }
        actions.put(UserEvent.UserEventType.USER_UPDATED) { event ->
            val userUpdatedEvent = event.getPayloadAs<UserUpdatedEvent>()
            log.info { "User Updated Event: $userUpdatedEvent" }
            quizUserService.updateUser(UserId(userUpdatedEvent.userId), userUpdatedEvent.toProfile())
        }
        actions.put(UserEvent.UserEventType.USER_DELETED) { event ->
            val userDeletedEvent = event.getPayloadAs<UserDeletedEvent>()
            log.info { "User Deleted Event: $userDeletedEvent" }
            quizUserService.deleteUser(UserId(userDeletedEvent.userId))
        }
    }

}