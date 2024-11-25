package com.grepp.quizy.game.domain.quiz

data class GameQuiz(
    val id: Long,
    val content: String,
    val option: List<GameQuizOption>,
    val answer: GameQuizAnswer
) {
}

data class GameQuizOption(
    val optionNumber: Int,
    val content: String
) {

}

data class GameQuizAnswer(
    val content: String,
    val explanation: String
) {

}