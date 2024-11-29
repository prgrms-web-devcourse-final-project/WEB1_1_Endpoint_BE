package com.grepp.quizy.quiz.infra.quiz.repository

interface QuizJpaBatchRepository {

    fun likeCountBatchUpdate(updates: Map<Long, Long>)

    fun selectionCountBatchUpdate(updates: Map<Long, Map<Int, Long>>)
}
