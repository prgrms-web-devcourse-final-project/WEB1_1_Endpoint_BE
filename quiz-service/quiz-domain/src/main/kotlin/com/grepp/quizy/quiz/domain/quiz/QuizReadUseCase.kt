package com.grepp.quizy.quiz.domain.quiz

interface QuizReadUseCase {

    fun getQuizTags(ids: List<QuizTagId>): List<QuizTag>
}
