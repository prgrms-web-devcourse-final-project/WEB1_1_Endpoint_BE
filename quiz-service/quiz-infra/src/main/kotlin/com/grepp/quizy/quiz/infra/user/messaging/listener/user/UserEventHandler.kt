package com.grepp.quizy.quiz.infra.user.messaging.listener.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.quiz.domain.user.QuizUserService
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.infra.user.messaging.listener.AbstractEventHandler
import com.grepp.quizy.quiz.infra.user.messaging.listener.EventHandler
import com.grepp.quizy.quiz.infra.user.messaging.listener.getPayloadAs
import org.springframework.stereotype.Component

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
            quizUserService.createUser(UserId(userCreatedEvent.userId), userCreatedEvent.toProfile())
        }
        actions.put(UserEvent.UserEventType.USER_UPDATED) { event ->
            val userUpdatedEvent = event.getPayloadAs<UserUpdatedEvent>()
            quizUserService.updateUser(UserId(userUpdatedEvent.userId), userUpdatedEvent.toProfile())
        }
        actions.put(UserEvent.UserEventType.USER_DELETED) { event ->
            val userDeletedEvent = event.getPayloadAs<UserDeletedEvent>()
            quizUserService.deleteUser(UserId(userDeletedEvent.userId))
        }
    }

}