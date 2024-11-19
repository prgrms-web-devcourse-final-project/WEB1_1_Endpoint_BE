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
        @Enumerated(EnumType.STRING) val quizType: QuizType,
        val choice: String,
        val isCorrect: Boolean?,
) {

    fun toDomain(): UserAnswer {
        val domainChoice =
                when (quizType) {
                    QuizType.OX ->
                            Choice.create(
                                    type = QuizType.OX,
                                    value = choice,
                                    isCorrect =
                                            requireNotNull(
                                                    isCorrect
                                            ) {
                                                "OX 퀴즈는 정답 여부가 필수입니다."
                                            },
                            )
                    QuizType.MULTIPLE_CHOICE ->
                            Choice.create(
                                    type = QuizType.MULTIPLE_CHOICE,
                                    value = choice,
                                    isCorrect =
                                            requireNotNull(
                                                    isCorrect
                                            ) {
                                                "객관식 퀴즈는 정답 여부가 필수입니다."
                                            },
                            )
                    QuizType.AB_TEST ->
                            Choice.create(
                                    type = QuizType.AB_TEST,
                                    value = choice,
                            )
                }

        return UserAnswer(id = id.toDomain(), choice = domainChoice)
    }

    companion object {
        fun from(domain: UserAnswer): UserAnswerEntity {
            return when (val choice = domain.choice) {
                is Choice.OXChoice ->
                        createAnswerable(
                                id = domain.id,
                                quizType = QuizType.OX,
                                choice = choice.value,
                                isCorrect = choice.isCorrect,
                        )
                is Choice.MultipleChoice ->
                        createAnswerable(
                                id = domain.id,
                                quizType = QuizType.MULTIPLE_CHOICE,
                                choice = choice.value,
                                isCorrect = choice.isCorrect,
                        )
                is Choice.ABChoice ->
                        createNonAnswerable(
                                id = domain.id,
                                quizType = QuizType.AB_TEST,
                                choice = choice.value,
                        )
            }
        }

        private fun createAnswerable(
                id: UserAnswerId,
                quizType: QuizType,
                choice: String,
                isCorrect: Boolean,
        ): UserAnswerEntity {
            require(quizType != QuizType.AB_TEST) {
                "AB 테스트는 createNonValidatableEntity()를 사용하세요."
            }
            return UserAnswerEntity(
                    id = UserAnswerEntityId.from(id),
                    quizType = quizType,
                    choice = choice,
                    isCorrect = isCorrect,
            )
        }

        private fun createNonAnswerable(
                id: UserAnswerId,
                quizType: QuizType,
                choice: String,
        ): UserAnswerEntity {
            require(quizType == QuizType.AB_TEST) {
                "AB 테스트가 아닌 퀴즈는 createValidatableEntity()를 사용하세요."
            }
            return UserAnswerEntity(
                    id = UserAnswerEntityId.from(id),
                    quizType = quizType,
                    choice = choice,
                    isCorrect = null,
            )
        }
    }
}
