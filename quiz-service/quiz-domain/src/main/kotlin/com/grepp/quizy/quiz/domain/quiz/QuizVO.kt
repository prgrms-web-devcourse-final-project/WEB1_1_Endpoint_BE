package com.grepp.quizy.quiz.domain.quiz

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.grepp.quizy.common.NoArg
import com.grepp.quizy.quiz.domain.image.QuizImageId

@JvmInline
value class QuizId(val value: Long)

@JvmInline
value class QuizTagId(val value: Long)

@NoArg
data class QuizTag(
    val name: String,
    val id: QuizTagId = QuizTagId(0),
) {

    companion object {
        fun create(name: String): QuizTag {
            return QuizTag(name)
        }

        fun of(name: String, id: QuizTagId): QuizTag {
            return QuizTag(name, id)
        }
    }
}

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = QuizOption.ABTestOption::class, name = "abTest"),
    JsonSubTypes.Type(value = QuizOption.MultipleChoiceOption::class, name = "multipleChoice"),
    JsonSubTypes.Type(value = QuizOption.OXOption::class, name = "ox")
)
@NoArg
sealed class QuizOption(
    val optionNumber: Int,
    val content: String,
    val selectionCount: Long,
) {

    abstract fun update(newOption: QuizOption): QuizOption

    @NoArg
    class ABTestOption(
        optionNumber: Int,
        content: String,
        val imageId: QuizImageId?,
        selectionCount: Long = 0
    ) : QuizOption(optionNumber, content, selectionCount) {

        override fun update(newOption: QuizOption): QuizOption {
            require(newOption is ABTestOption) { "ABTestOption can only be updated with ABTestOption" }
            return ABTestOption(
                optionNumber,
                newOption.content,
                newOption.imageId,
                selectionCount
            )
        }
    }

    @NoArg
    class MultipleChoiceOption(
        optionNumber: Int,
        content: String,
        selectionCount: Long = 0
    ) : QuizOption(optionNumber, content, selectionCount) {

        override fun update(newOption: QuizOption): QuizOption {
            return MultipleChoiceOption(optionNumber, newOption.content, selectionCount)
        }
    }

    @NoArg
    class OXOption(
        optionNumber: Int,
        content: String,
        selectionCount: Long = 0,
    ) : QuizOption(optionNumber, content, selectionCount) {

        override fun update(newOption: QuizOption): QuizOption {
            return OXOption(optionNumber, newOption.content, selectionCount)
        }
    }
}

@NoArg
data class QuizContent(
        val category: QuizCategory,
        val content: String,
        val tags: List<QuizTag>,
        val options: List<QuizOption>,
) {

    init {
        require(content.isNotBlank()) { "퀴즈 내용은 공백일 수 없습니다" }
    }

    fun updateTags(tags: List<QuizTag>): QuizContent =
            copy(tags = tags)

    fun update(updateContent: QuizContent): QuizContent {
        return copy(
            category = updateContent.category,
            content = updateContent.content,
            tags = updateContent.tags,
            options = updateContent.options.map { newOption ->
                options.find { it.optionNumber == newOption.optionNumber }?.update(newOption)
                    ?: throw IllegalArgumentException("OptionNumber ${newOption.optionNumber} 를 찾을수 없습니다")
            }
        )
    }
}

@NoArg
data class QuizAnswer(val answerNumber: Int, val explanation: String) {

    fun isCorrect(userChoice: Int): Boolean = answerNumber == userChoice

    companion object {
        val NONE: QuizAnswer = QuizAnswer(0, "")
    }
}

@NoArg
data class QuizCount(val like: Long = 0, val comment: Long = 0)

enum class QuizType(val value: String) {
    AB_TEST("AB 테스트"),
    OX("OX 퀴즈"),
    MULTIPLE_CHOICE("객관식"),
}

enum class QuizCategory(val description: String) {
    ALGORITHM("알고리즘"),
    PROGRAMMING_LANGUAGE("프로그래밍 언어"),
    NETWORK("네트워크"),
    OPERATING_SYSTEM("운영체제"),
    WEB_DEVELOPMENT("웹 개발"),
    MOBILE("모바일"),
    DEV_OPS("데브옵스"),
    DATABASE("데이터베이스"),
    SOFTWARE_ENGINEERING("소프트웨어 공학"),
}

enum class QuizDifficulty {
    EASY, MEDIUM, HARD, RANDOM, NONE
}