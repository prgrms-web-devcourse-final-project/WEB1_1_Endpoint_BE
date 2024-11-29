package com.grepp.quizy.quiz.domain.quiz


interface Answerable {

    fun getQuizAnswer(): QuizAnswer

    fun getCorrectAnswer(): Int = getQuizAnswer().answerNumber

    fun getAnswerExplanation(): String = getQuizAnswer().explanation

    fun updateAnswer(newAnswer: QuizAnswer): Answerable

    fun validateAnswer(range: Int) {
        require(getCorrectAnswer() in 1..range) { "정답은 1부터 $range 사이여야 합니다." }
    }

    fun isCorrect(userAnswer: Int): Boolean =
            getCorrectAnswer() == userAnswer

    fun getCorrectRate(): Double

    fun getDifficulty(): QuizDifficulty
}
