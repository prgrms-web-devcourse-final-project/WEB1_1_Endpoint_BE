package com.grepp.quizy.matching.infra.user.messaging.listener.quiz

import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.matching.infra.user.messaging.listener.AbstractEventHandler
import com.grepp.quizy.matching.infra.user.messaging.listener.EventHandler
import com.grepp.quizy.matching.infra.user.messaging.listener.getPayloadAs
import com.grepp.quizy.matching.infra.user.repository.UserJpaRepository
import org.springframework.stereotype.Component

@Component
class QuizEventHandler(
    mapper: ObjectMapper,
    private val userJpaRepository: UserJpaRepository
) : AbstractEventHandler<QuizEvent>(mapper), EventHandler {
    init {
        initActions()
    }

    final override fun initActions() {
        actions.put(QuizEvent.QuizEventType.USER_INTERESTS_INITIALIZED) { event ->
            val interestEvent = event.getPayloadAs<UserInterestInitializedEvent>()
            userJpaRepository.save(interestEvent.MatchingUserEntity())
        }
    }

}