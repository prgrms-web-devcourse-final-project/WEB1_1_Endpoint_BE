package com.grepp.quizy.search.domain.quiz

@JvmInline value class QuizId(val value: Long)

@JvmInline value class QuizTag(val value: String)

@JvmInline value class QuizCategory(val value: String)

data class QuizContent(val type: QuizType, val value: String)

data class QuizOption(val optionNumber: Int, val content: String, val selectionCount: Int)

data class QuizAnswer(val value: String, val explanation: String)

data class QuizCount(val like: Int = 0, val comment: Int = 0) {
    init {
        require(like >= 0) { "좋아요 수는 음수가 될 수 없습니다. $like" }
        require(comment >= 0) { "댓글 수는 음수가 될 수 없습니다. $comment" }
    }
}

<<<<<<< Updated upstream
enum class QuizType(val typeName: String) {
    OX("OX 퀴즈"), AB("A/B 밸런스"), MULTIPLE_CHOICE("객관식");
}
=======
data class QuizChoice(
        val choices: List<String>,
        val answer: String,
        val explanation: String,
)

data class QuizLikeCount(val value: Int) {
    init {
        require(value >= 0) {
            "Like count must be greater than or equal to zero: $value"
        }
    }
}

data class QuizCommentCount(val value: Int) {
    init {
        require(value >= 0) {
            "Comment count must be greater than or equal to zero: $value"
        }
    }
}
>>>>>>> Stashed changes
