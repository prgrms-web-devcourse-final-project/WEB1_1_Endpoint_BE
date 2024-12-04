package com.grepp.quizy.quiz.infra.user.messaging.listener

import com.grepp.quizy.quiz.infra.user.repository.QuizUserJpaRepository
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.data.repository.findByIdOrNull
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class UserEventKafkaListener(
    private val quizUserJpaRepository: QuizUserJpaRepository
) {

    //TODO: Outbox 패턴에 맞게 리팩토링
    @KafkaListener(topics = ["user"], groupId = "quiz-group")
    fun receive(records: List<ConsumerRecord<String, UserEvent>>, acknowledgment: Acknowledgment) {
        records.forEach { record ->
            handleUserEvent(record.value())
        }
        acknowledgment.acknowledge()
    }

    private fun handleUserEvent(event: UserEvent) {
        when (event) {
            is UserEvent.Created -> quizUserJpaRepository.save(event.toEntity())
            is UserEvent.Updated -> handleUserUpdated(event)
            is UserEvent.Deleted -> quizUserJpaRepository.deleteById(event.userId)
        }
    }

    private fun handleUserUpdated(event: UserEvent.Updated) {
        quizUserJpaRepository.findByIdOrNull(event.userId)?.let { entity ->
            entity.update(event.name, event.profileImageUrl)
            quizUserJpaRepository.save(entity)
        }
    }
}