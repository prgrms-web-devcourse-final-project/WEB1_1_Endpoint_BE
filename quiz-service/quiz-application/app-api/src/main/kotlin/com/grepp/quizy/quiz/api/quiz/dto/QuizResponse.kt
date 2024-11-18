import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.grepp.quizy.quiz.domain.quiz.*

@JsonInclude(Include.NON_NULL)
sealed class QuizResponse {
    abstract val id: Long
    abstract val type: QuizType
    abstract val content: String
    abstract val tags: List<String>
    abstract val options: List<QuizOption>

    data class ABTestResponse(
            override val id: Long,
            override val type: QuizType,
            override val content: String,
            override val tags: List<String>,
            override val options: List<QuizOption>,
    ) : QuizResponse()

    data class OXQuizResponse(
            override val id: Long,
            override val type: QuizType,
            override val content: String,
            override val tags: List<String>,
            override val options: List<QuizOption>,
            val answer: String,
            val explanation: String,
    ) : QuizResponse()

    data class MultipleChoiceQuizResponse(
            override val id: Long,
            override val type: QuizType,
            override val content: String,
            override val tags: List<String>,
            override val options: List<QuizOption>,
            val answer: String,
            val explanation: String,
    ) : QuizResponse()

    companion object {
        fun from(quiz: Quiz): QuizResponse {
            return when (quiz) {
                is ABTest ->
                        ABTestResponse(
                                id = quiz.id.value,
                                type = quiz.type,
                                content =
                                        quiz.content
                                                .content,
                                tags =
                                        quiz.content.tags
                                                .map {
                                                    it.name
                                                },
                                options =
                                        quiz.content.options,
                        )

                is OXQuiz ->
                        OXQuizResponse(
                                id = quiz.id.value,
                                type = quiz.type,
                                content =
                                        quiz.content
                                                .content,
                                tags =
                                        quiz.content.tags
                                                .map {
                                                    it.name
                                                },
                                options =
                                        quiz.content
                                                .options,
                                answer =
                                        quiz
                                                .getCorrectAnswer(),
                                explanation =
                                        quiz
                                                .getAnswerExplanation(),
                        )

                is MultipleChoiceQuiz ->
                        MultipleChoiceQuizResponse(
                                id = quiz.id.value,
                                type = quiz.type,
                                content =
                                        quiz.content
                                                .content,
                                tags =
                                        quiz.content.tags
                                                .map {
                                                    it.name
                                                },
                                options =
                                        quiz.content
                                                .options,
                                answer =
                                        quiz
                                                .getCorrectAnswer(),
                                explanation =
                                        quiz
                                                .getAnswerExplanation(),
                        )

                else ->
                        throw IllegalArgumentException(
                                "지원하지 않는 퀴즈 타입입니다"
                        )
            }
        }
    }
}
