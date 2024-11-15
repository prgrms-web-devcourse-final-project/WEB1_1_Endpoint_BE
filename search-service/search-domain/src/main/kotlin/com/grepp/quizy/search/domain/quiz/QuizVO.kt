package com.grepp.quizy.search.domain.quiz

data class QuizId(val value: Long)

data class QuizTag(val tags: List<String>) {

    fun buildTagString() = tags.joinToString(" ")

    companion object {
        fun from(tags: String) = QuizTag(tags.split(" "))
    }
}

data class QuizChoice(val choices: List<String>, val answer: String, val explanation: String)

data class QuizLike(val numLikes: Int)

data class QuizComment(val numComments: Int)