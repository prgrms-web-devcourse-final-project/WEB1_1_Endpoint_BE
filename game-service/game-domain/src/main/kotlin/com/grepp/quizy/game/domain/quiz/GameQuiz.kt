package com.grepp.quizy.game.domain.quiz

class GameQuiz(
    val id: Long,
    val content: String, // 퀴즈 질문
    val option: List<GameQuizOption>,
    val answer: GameQuizAnswer
) {
}

data class GameQuizOption(
    // OX면 2개, 객관식이면 객관식 수만큼
    val no: Int, // DB를 위한 필드 내가 쓸 일 없을듯
    val content: String // O or X 객관식이면 객관식 답변
) {

}

data class GameQuizAnswer(
    val content: String, // 옵션의 content에 들어있는 값 중 정답인 값
    val explanation: String // 정답에 대한 설명
) {

}