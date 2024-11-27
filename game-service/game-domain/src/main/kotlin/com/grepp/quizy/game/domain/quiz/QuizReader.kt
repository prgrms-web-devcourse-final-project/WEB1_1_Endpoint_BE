package com.grepp.quizy.game.domain.quiz

import org.springframework.stereotype.Component

@Component
class QuizReader(
    private val quizRepository: QuizRepository
) {

    fun read(id: Long) =
        quizRepository.findById(id) ?: throw IllegalArgumentException("퀴즈를 찾을 수 없습니다.")

}