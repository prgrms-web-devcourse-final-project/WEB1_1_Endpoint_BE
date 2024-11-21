package com.grepp.quizy.quiz.infra.quiz.repository

interface QuizJpaBatchRepository {

    fun batchUpdate(updates: Map<Long, Long>)
}
