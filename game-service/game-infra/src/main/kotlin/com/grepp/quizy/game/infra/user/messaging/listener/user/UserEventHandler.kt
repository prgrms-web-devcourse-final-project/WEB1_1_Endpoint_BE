package com.grepp.quizy.game.infra.user.messaging.listener.user

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.game.domain.user.UserAppender
import com.grepp.quizy.game.domain.user.UserRemover
import com.grepp.quizy.game.domain.user.UserUpdater
import com.grepp.quizy.game.infra.user.messaging.listener.AbstractEventHandler
import com.grepp.quizy.game.infra.user.messaging.listener.EventHandler
import com.grepp.quizy.game.infra.user.messaging.listener.getPayloadAs
import org.springframework.stereotype.Component

@Component
class UserEventHandler(
    mapper: ObjectMapper,
    private val userAppender: UserAppender,
    private val userUpdater: UserUpdater,
    private val userRemover: UserRemover
) : AbstractEventHandler<UserEvent>(mapper), EventHandler {
    init {
        initActions()
    }

    final override fun initActions() {
        actions.put(UserEvent.UserEventType.USER_CREATED) { event ->
            val userCreatedEvent = event.getPayloadAs<UserCreatedEvent>()
            userAppender.append(userCreatedEvent.toUser())
        }
        actions.put(UserEvent.UserEventType.USER_UPDATED) { event ->
            val userUpdatedEvent = event.getPayloadAs<UserUpdatedEvent>()
            userUpdater.update(userUpdatedEvent.toUser())
        }
        actions.put(UserEvent.UserEventType.USER_DELETED) { event ->
            val userDeletedEvent = event.getPayloadAs<UserDeletedEvent>()
            userRemover.remove(userDeletedEvent.userId)
        }
    }

}