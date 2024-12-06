package com.grepp.quizy.quiz.infra.quizread.document

import com.grepp.quizy.quiz.domain.quiz.QuizDifficulty
import com.grepp.quizy.quiz.domain.quiz.QuizType
import kotlin.math.ln

class QuizDifficultyUtil {
    companion object {

        fun calculateDifficulty(quiz: QuizDocument): QuizDifficulty? {
            if (quiz.type == QuizType.AB_TEST) return null

            val correctionRatio = quiz.calculateCorrectionRatio()
            val difficultyScore = correctionRatio * ln((quiz.totalAnsweredUsers + 1).toDouble())
            return QuizDifficulty.of(difficultyScore)
        }
    }
}