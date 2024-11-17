import com.grepp.quizy.quiz.domain.QuizAnswer
import com.grepp.quizy.quiz.domain.QuizContent
import com.grepp.quizy.quiz.domain.QuizOption
import com.grepp.quizy.quiz.domain.QuizType
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class QuizContentVO(
        @Enumerated(EnumType.STRING) val type: QuizType,
        val content: String,
) {
    protected constructor() : this(QuizType.AB_TEST, "")

    fun toDomain(): QuizContent {
        return QuizContent(type = type, content = content)
    }

    companion object {
        fun from(content: QuizContent): QuizContentVO {
            return QuizContentVO(
                    type = content.type,
                    content = content.content,
            )
        }
    }
}

@Embeddable
data class QuizOptionVO(
        val optionNumber: Int,
        val title: String,
        val content: String,
) {
    protected constructor() : this(0, "", "")

    fun toDomain(): QuizOption {
        return QuizOption(
                optionNumber = optionNumber,
                title = title,
                content = content,
        )
    }

    companion object {
        fun from(option: QuizOption): QuizOptionVO {
            return QuizOptionVO(
                    optionNumber = option.optionNumber,
                    title = option.title,
                    content = option.content,
            )
        }
    }
}

@Embeddable
data class QuizAnswerVO(
        val value: String,
        val explanation: String,
) {
    protected constructor() : this("", "")

    fun toDomain(): QuizAnswer {
        return QuizAnswer(
                value = value,
                explanation = explanation,
        )
    }

    companion object {
        fun from(answer: QuizAnswer): QuizAnswerVO {
            return QuizAnswerVO(
                    value = answer.value,
                    explanation = answer.explanation,
            )
        }
    }
}
