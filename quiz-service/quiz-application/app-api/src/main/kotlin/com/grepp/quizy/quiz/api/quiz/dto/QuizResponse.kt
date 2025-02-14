import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.grepp.quizy.quiz.api.quiz.dto.QuizOptionResponse
import com.grepp.quizy.quiz.domain.quiz.*
import java.time.LocalDateTime

@JsonInclude(Include.NON_NULL)
sealed class QuizResponse {
    abstract val id: Long
    abstract val category: QuizCategory
    abstract val type: QuizType
    abstract val content: String
    abstract val tags: List<String>
    abstract val options: List<QuizOptionResponse>
    abstract val createdAt: LocalDateTime
    abstract val modifiedAt: LocalDateTime

    data class ABTestResponse(
            override val id: Long,
            override val category: QuizCategory,
            override val type: QuizType,
            override val content: String,
            override val tags: List<String>,
            override val options: List<QuizOptionResponse>,
            override val createdAt: LocalDateTime,
            override val modifiedAt: LocalDateTime,
    ) : QuizResponse()

    data class OXQuizResponse(
        override val id: Long,
        override val category: QuizCategory,
        override val type: QuizType,
        override val content: String,
        override val tags: List<String>,
        override val options: List<QuizOptionResponse>,
        override val createdAt: LocalDateTime,
        override val modifiedAt: LocalDateTime,
        val answerNumber: Int,
        val explanation: String,
        val correctRate: Double,
    ) : QuizResponse()

    data class MultipleChoiceQuizResponse(
            override val id: Long,
            override val category: QuizCategory,
            override val type: QuizType,
            override val content: String,
            override val tags: List<String>,
            override val options: List<QuizOptionResponse>,
            override val createdAt: LocalDateTime,
            override val modifiedAt: LocalDateTime,
            val answerNumber: Int,
            val explanation: String,
            val correctRate: Double,
    ) : QuizResponse()

    companion object {
        fun from(quiz: Quiz): QuizResponse {
            return when (quiz) {
                is ABTest ->
                        ABTestResponse(
                                id = quiz.id.value,
                                category = quiz.content.category,
                                type = quiz.type,
                                content = quiz.content.content,
                                tags =
                                        quiz.content.tags.map {
                                            it.name
                                        },
                                options = quiz.content.options.map { QuizOptionResponse.from(it) },
                                createdAt = quiz.dateTime.createdAt,
                                modifiedAt = quiz.dateTime.updatedAt,
                        )

                is OXQuiz ->
                        OXQuizResponse(
                                id = quiz.id.value,
                                category = quiz.content.category,
                                type = quiz.type,
                                content = quiz.content.content,
                                tags =
                                        quiz.content.tags.map {
                                            it.name
                                        },
                                options = quiz.content.options.map { QuizOptionResponse.from(it) },
                                answerNumber = quiz.getCorrectAnswer(),
                                explanation =
                                        quiz.getAnswerExplanation(),
                                createdAt = quiz.dateTime.createdAt,
                                modifiedAt = quiz.dateTime.updatedAt,
                                correctRate = quiz.getCorrectRate(),
                        )

                is MultipleChoiceQuiz ->
                        MultipleChoiceQuizResponse(
                                id = quiz.id.value,
                                category = quiz.content.category,
                                type = quiz.type,
                                content = quiz.content.content,
                                tags =
                                        quiz.content.tags.map {
                                            it.name
                                        },
                                options = quiz.content.options.map { QuizOptionResponse.from(it) },
                                answerNumber = quiz.getCorrectAnswer(),
                                explanation =
                                        quiz.getAnswerExplanation(),
                                createdAt = quiz.dateTime.createdAt,
                                modifiedAt = quiz.dateTime.updatedAt,
                                correctRate = quiz.getCorrectRate(),
                        )

                else ->
                        throw IllegalArgumentException(
                                "지원하지 않는 퀴즈 타입입니다"
                        )
            }
        }
    }
}
