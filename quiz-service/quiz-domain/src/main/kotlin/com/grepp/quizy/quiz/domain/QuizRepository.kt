package com.grepp.quizy.quiz.domain

interface QuizRepository {
    fun save(quiz: Quiz): Quiz

    fun findTagsByNameIn(name: List<String>): List<QuizTag>
}
