package com.grepp.quizy.user.infra.user.messaging.listener.quiz

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.user.domain.user.UserId
import com.grepp.quizy.user.domain.user.UserService
import com.grepp.quizy.user.infra.user.messaging.listener.AbstractEventHandler
import com.grepp.quizy.user.infra.user.messaging.listener.EventHandler
import com.grepp.quizy.user.infra.user.messaging.listener.getPayloadAs
import org.springframework.stereotype.Component

@Component
class QuizEventHandler(
    mapper: ObjectMapper,
    private val userService: UserService
) : AbstractEventHandler<QuizEvent>(mapper), EventHandler {
    init {
        initActions()
    }

    final override fun initActions() {
        actions.put(QuizEvent.QuizEventType.USER_INTERESTS_INITIALIZED) { event ->
            val interestEvent = event.getPayloadAs<UserInterestInitializedEvent>()
            userService.changeRole(UserId(interestEvent.userId))
        }
    }

}