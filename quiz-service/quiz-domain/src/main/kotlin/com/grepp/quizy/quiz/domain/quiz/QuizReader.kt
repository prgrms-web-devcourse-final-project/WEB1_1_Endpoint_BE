package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.quiz.domain.quiz.exception.QuizNotFoundException
import org.springframework.stereotype.Component

@Component
class QuizReader(private val quizRepository: QuizRepository) {
    fun read(id: QuizId): Quiz {
        return quizRepository.findById(id) ?: throw QuizNotFoundException
    }

    fun readTags(ids: List<QuizTagId>): List<QuizTag> {
        return quizRepository.findTagsByInId(ids)
    }
}
