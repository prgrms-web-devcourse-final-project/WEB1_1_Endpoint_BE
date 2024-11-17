package com.grepp.quizy.search.domain.quiz

data class QuizTag(private val tags: List<String>) {

    fun buildTagString() = tags.joinToString(" ")

    companion object {
        fun from(tags: String) = QuizTag(tags.split(" "))
    }
}

data class QuizChoice(val choices: List<String>, val answer: String, val explanation: String)

data class QuizLikeCount(val value: Int) {
    init {
        require(value >= 0) { "Like count must be greater than or equal to zero: $value" }
    }
}

data class QuizCommentCount(val value: Int) {
    init {
        require(value >= 0) { "Comment count must be greater than or equal to zero: $value" }
    }
}