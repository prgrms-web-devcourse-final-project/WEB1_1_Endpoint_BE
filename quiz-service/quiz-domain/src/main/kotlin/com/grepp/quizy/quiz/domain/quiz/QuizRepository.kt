package com.grepp.quizy.quiz.domain.quiz

interface QuizRepository {
    fun save(quiz: Quiz): Quiz

    fun findById(id: QuizId): Quiz?

    fun findByIdWithLock(id: QuizId): Quiz?

    fun findTagsByNameIn(names: List<String>): List<QuizTag>

    fun existsUserAnswerByQuizId(quizId: QuizId): Boolean

    fun delete(quiz: Quiz)

    fun saveTags(newTags: List<QuizTag>): List<QuizTag>

    fun findTagsByInId(ids: List<QuizTagId>): List<QuizTag>

    fun findCountsByInId(ids: List<QuizId>): QuizCountPackage
}
