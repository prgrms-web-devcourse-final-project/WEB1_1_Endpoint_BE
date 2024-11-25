package com.grepp.quizy.quiz.domain.quiz

data class QuizCountPackage(val counts: Map<QuizId, QuizCount>) {
    fun getCountOf(quizId: QuizId) = counts[quizId] ?: QuizCount()
}