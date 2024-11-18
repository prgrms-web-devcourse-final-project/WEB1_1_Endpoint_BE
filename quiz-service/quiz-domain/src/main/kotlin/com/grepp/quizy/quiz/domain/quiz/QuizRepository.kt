package com.grepp.quizy.quiz.domain.quiz

interface QuizRepository {
    fun save(quiz: Quiz): Quiz

    fun update(quiz: Quiz): Quiz

    fun findById(id: QuizId): Quiz?

    fun findTagsByNameIn(name: List<String>): List<QuizTag>

    fun delete(quiz: Quiz)

    fun saveTags(newTags: List<QuizTag>): List<QuizTag>
}
