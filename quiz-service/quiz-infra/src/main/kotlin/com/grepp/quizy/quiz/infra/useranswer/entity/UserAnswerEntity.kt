package com.grepp.quizy.quiz.infra.useranswer.entity

import com.grepp.quizy.quiz.domain.quiz.QuizType
import com.grepp.quizy.quiz.domain.useranswer.Choice
import com.grepp.quizy.quiz.domain.useranswer.UserAnswer
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerId
import jakarta.persistence.*

@Entity
@Table(name = "user_answers")
class UserAnswerEntity(
        @EmbeddedId val id: UserAnswerEntityId,
        val choice: Int,
        val isCorrect: Boolean? = null,
) {

    fun toDomain(): UserAnswer {
        val domainChoice =
                when (isCorrect != null) {
                    true -> Choice.create(choice, isCorrect)
                    false -> Choice.create(choice)
                }

        return UserAnswer(id = id.toDomain(), choice = domainChoice)
    }

    companion object {
        fun from(domain: UserAnswer): UserAnswerEntity {
            return when (val choice = domain.choice) {
                is Choice.AnswerableChoice ->
                    UserAnswerEntity(
                        id = UserAnswerEntityId.from(domain.id),
                        choice = choice.choiceNumber,
                        isCorrect = choice.isCorrect,
                    )

                is Choice.NonAnswerableChoice ->
                    UserAnswerEntity(
                        id = UserAnswerEntityId.from(domain.id),
                        choice = choice.choiceNumber
                    )
            }
        }
    }
}
